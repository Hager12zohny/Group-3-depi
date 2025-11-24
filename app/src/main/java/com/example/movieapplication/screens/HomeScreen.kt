package com.example.movieapplication.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.movieapplication.model.Movie
import com.example.movieapplication.viewmodel.HomeViewModel
import kotlinx.coroutines.delay
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.ui.graphics.graphicsLayer

@Composable
fun MovieHomeScreen(
    homeViewModel: HomeViewModel,
    onMovieClick: (Int) -> Unit,
    clickOnSearch: () -> Unit
) {
    val newReleases by homeViewModel.newReleases.collectAsState()
    val trending by homeViewModel.trendingMovies.collectAsState()
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    val headerMovies = remember(trending) { trending.take(5) }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.Black, Color(0xFF9C27B0))
                )
            )
            .padding(8.dp)
    ) {
        // Search Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable { clickOnSearch() }
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search movies...", color = Color.White) }
                ,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search",
                        tint = Color(0xFF9C27B0)
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF9C27B0),
                    unfocusedBorderColor = Color(0xFF9C27B0),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    cursorColor = Color(0xFF9C27B0)
                ),
                enabled = false
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (headerMovies.isNotEmpty()) {
            PagerHeaderSlider(
                movies = headerMovies,
                onMovieClick = onMovieClick,
                scrollOffset = scrollState.value
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // New Releases Section
        Text(
            text = "New Releases",
            fontSize = 18.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp)
        )

        MovieSection(
            movies = newReleases.filter { it.title.contains(searchQuery.text, ignoreCase = true) },
            onMovieClick = onMovieClick
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Trending Section
        Text(
            text = "Trending",
            fontSize = 18.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp)
        )

        MovieSection(
            movies = trending.filter { it.title.contains(searchQuery.text, ignoreCase = true) },
            onMovieClick = onMovieClick
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerHeaderSlider(
    movies: List<Movie>,
    onMovieClick: (Int) -> Unit,
    scrollOffset: Int
) {
    val pagerState = rememberPagerState(pageCount = { movies.size })

    // Auto scroll
    LaunchedEffect(pagerState) {
        while (true) {
            delay(5000)
            val next = (pagerState.currentPage + 1) % movies.size
            pagerState.animateScrollToPage(next)
        }
    }

    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) { page ->
        val movie = movies[page]

        val parallaxOffset = (scrollOffset * 0.3f).coerceAtMost(150f)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable { onMovieClick(movie.id) }
        ) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500${movie.poster_path}",
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        translationY = -parallaxOffset // تأثير الـ parallax
                    }
            )

            // Overlay Text
            Text(
                text = movie.title,
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(12.dp)
                    .background(Color(0x55000000))
            )
        }
    }
}

@Composable
fun MovieSection(
    movies: List<Movie>,
    onMovieClick: (Int) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        items(movies) { movie ->
            MovieCard(movie = movie, onClick = { onMovieClick(movie.id) })
        }
    }
}

@Composable
fun MovieCard(movie: Movie, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(140.dp)
            .height(200.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.DarkGray),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500${movie.poster_path}",
            contentDescription = movie.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}
