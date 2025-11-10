package com.example.movieapplication.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import com.example.movieapplication.R
import com.example.movieapplication.model.Movie
import com.example.movieapplication.viewmodel.HomeViewModel
import kotlinx.coroutines.delay
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState

@Composable
fun MovieHomeScreen(
    homeViewModel: HomeViewModel,
    onMovieClick: (Int) -> Unit,
    clickOnSearch: () -> Unit
) {
    val newReleases by homeViewModel.newReleases.collectAsState()
    val trending by homeViewModel.trendingMovies.collectAsState()
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    val headerMovies = remember(trending) {
        if (trending.isNotEmpty()) {
            listOf(
                Movie(
                    id = -1,
                    title = "Mission Impossible",
                    poster_path = "",
                    overview = "Ethan Hunt embarks on a dangerous mission..."
                )
            ) + trending.take(2)
        } else emptyList()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.Black, Color(0xFF9C27B0))
                )
            )
            .padding(8.dp)
    ) {
        // Search Box
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            placeholder = { Text("Search movies...", color = Color(0xFF9C27B0)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search",
                    tint = Color(0xFF9C27B0),
                    modifier = Modifier.clickable { clickOnSearch() }
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF9C27B0),
                unfocusedBorderColor = Color(0xFF9C27B0),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = Color(0xFF9C27B0)
            )


        )

        Spacer(modifier = Modifier.height(16.dp))

        if (headerMovies.isNotEmpty()) {
            PagerHeaderSlider(
                movies = headerMovies,
                onMovieClick = onMovieClick
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
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerHeaderSlider(
    movies: List<Movie>,
    onMovieClick: (Int) -> Unit
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable { if (movie.id != -1) onMovieClick(movie.id) }
        ) {
            Text(
                text = movie.title,
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(8.dp)
            )

            val imageModel = if (movie.id == -1) R.drawable.missionimpossible
            else "https://image.tmdb.org/t/p/w500${movie.poster_path}"

            AsyncImage(
                model = imageModel,
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
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
        val imageModel = if (movie.id == -1) R.drawable.missionimpossible
        else "https://image.tmdb.org/t/p/w500${movie.poster_path}"

        AsyncImage(
            model = imageModel,
            contentDescription = movie.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

