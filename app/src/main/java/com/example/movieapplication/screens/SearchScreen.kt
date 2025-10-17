package com.example.movieapplication.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movieapplication.R


@Composable
fun MovieScreen() {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val fallbackRes = R.drawable.ic_launcher_background

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF4A235A))  //dark purple
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Search Bar 
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp)),
            placeholder = { Text("Search movies...", color = Color.White.copy(alpha = 0.7f)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            },
            singleLine = true
        )

        // Headline
        Text(
            text = "The Popular Movies",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        // Side-by-Side Layout
         Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(1f)  
                .padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)  //  gap 
        ) {
            // Left Section 
            MovieSection(
                movies = listOf(
                    SearchMovie("Harry Potter", R.drawable.harry_potter.takeIf { true } ?: fallbackRes, 100.dp),
                    SearchMovie("Interstellar", R.drawable.interstellar.takeIf { true } ?: fallbackRes, 105.dp),
                    SearchMovie("The Matrix", R.drawable.the_matrix.takeIf { true } ?: fallbackRes, 120.dp),
                    SearchMovie("Titanic", R.drawable.titanic_poster.takeIf { true } ?: fallbackRes, 100.dp)
                ),
                modifier = Modifier.weight(1.5f)              )

            // Right Section 
            MovieSection(
                movies = listOf(
                    SearchMovie("The Maze Runner", R.drawable.the_maze_runner.takeIf { true } ?: fallbackRes, 120.dp),
                    SearchMovie("xXx", R.drawable.xxx_poster.takeIf { true } ?: fallbackRes, 100.dp),
                    SearchMovie("Inception", R.drawable.inception_poster.takeIf { true } ?: fallbackRes, 100.dp),
                    SearchMovie("Avatar", R.drawable.avatar.takeIf { true } ?: fallbackRes, 105.dp)
                ),
                modifier = Modifier.weight(1.5f)  
            )
        }
    }
}

// Data of the Movie
data class SearchMovie(val title: String, val posterRes: Int, val imageHeight: Dp)


@Composable
fun MovieSection(
    movies: List<SearchMovie>,
    modifier: Modifier = Modifier
) {
    Card(  
        modifier = modifier
            .fillMaxHeight()  
            .padding(4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),  
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            movies.forEach { movie ->
                Image(
                    painter = painterResource(id = movie.posterRes),
                    contentDescription = movie.title,
                    contentScale = ContentScale.Crop,  
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(movie.imageHeight)  
                )

                // Title 
                Text(
                    text = movie.title.trim(),
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MovieScreenPreview() {
    MovieScreen()
}
