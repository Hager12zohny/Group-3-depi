package com.example.movieapplication.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.movieapplication.viewmodel.SearchViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavHostController, viewModel: SearchViewModel = viewModel()) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val searchResults by viewModel.searchResults.collectAsState()
    val allMovies by viewModel.allMovies.collectAsState()
    val scope = rememberCoroutineScope()


    LaunchedEffect(Unit) {
        viewModel.fetchAllMovies()
    }


    val displayedMovies = if (searchQuery.text.isBlank()) allMovies else searchResults
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF4A235A))
            .padding(16.dp)
    ) {

        //Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                if (it.text.isBlank()) {
                    // if search bar cleared, show all movies again
                    scope.launch { viewModel.fetchAllMovies() }
                } else {
                    // otherwise perform search
                    scope.launch { viewModel.searchMovies(it.text) }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.medium),
            placeholder = { Text("Search movies...", color = Color.White.copy(alpha = 0.7f)) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color.White) },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color(0xFF6C3483),
                focusedContainerColor = Color(0xFF6C3483),
                cursorColor = Color.White,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        //Movies Grid
        if (displayedMovies.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No movies found", color = Color.White.copy(alpha = 0.7f))
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(4.dp)
            ) {
                items(displayedMovies) { movie ->
                    MovieCard(
                        title = movie.title,
                        posterPath = movie.poster_path,
                        onClick = {
                            navController.navigate("details/${movie.id}")
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun MovieCard(title: String, posterPath: String, onClick: () -> Unit) {
    val imageUrl = "https://image.tmdb.org/t/p/w500$posterPath"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(270.dp)
            .clip(MaterialTheme.shapes.medium),
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(MaterialTheme.shapes.medium)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = title,
                color = Color.White,
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .align(Alignment.CenterHorizontally),
                maxLines = 2
            )
        }
    }
}
