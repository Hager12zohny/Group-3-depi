package com.example.movieapplication.screens

package com.example.movieapplication.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip  // For Modifier.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movieapplication.R
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.ui.unit.Dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieScreen()
        }
    }
}

@Composable
fun MovieScreen() {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val fallbackRes = R.drawable.ic_launcher_background

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF4A235A))  //  solid dark purple
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Search Bar (mimics SearchView: white rounded background, white hint, search icon)
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
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                cursorColor = Color.White,
                containerColor = Color.White  // White background like search_background
            ),
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
                .fillMaxHeight(1f)  // Fills remaining space
                .padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)  // Small gap between sections
        ) {
            // Left Section (Main: ~2/3 width,
            MovieSection(
                movies = listOf(
                    Movie("Harry Potter", R.drawable.harry_potter.takeIf { true } ?: fallbackRes, 100.dp),
                    Movie("Interstellar", R.drawable.interstellar.takeIf { true } ?: fallbackRes, 105.dp),
                    Movie("The Matrix", R.drawable.the_matrix.takeIf { true } ?: fallbackRes, 120.dp),
                    Movie("Titanic", R.drawable.titanic_poster.takeIf { true } ?: fallbackRes, 100.dp)
                ),
                modifier = Modifier.weight(2f)  // ~2/3 width
            )

            // Right Section (Secondary: ~1/3 width,
            MovieSection(
                movies = listOf(
                    Movie("The Maze Runner", R.drawable.the_maze_runner.takeIf { true } ?: fallbackRes, 120.dp),
                    Movie("xXx", R.drawable.xxx_poster.takeIf { true } ?: fallbackRes, 100.dp),
                    Movie("Inception", R.drawable.inception_poster.takeIf { true } ?: fallbackRes, 100.dp),
                    Movie("Avatar", R.drawable.avatar.takeIf { true } ?: fallbackRes, 105.dp)
                ),
                modifier = Modifier.weight(1f)  // ~1/3 width
            )
        }
    }
}

// Data class for Movie
data class Movie(val title: String, val posterRes: Int, val imageHeight: Dp)


@Composable
fun MovieSection(
    movies: List<Movie>,
    modifier: Modifier = Modifier
) {
    Card(  // Mimics outer CardView: rounded 8dp, elevation 4dp, margin 4dp
        modifier = modifier
            .fillMaxHeight()  // Fills section height
            .padding(4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),  // #FFFFFF background
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
                    contentScale = ContentScale.Crop,  // Matches centerCrop
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(movie.imageHeight)  // Exact heights: 100dp, 105dp, etc.
                )

                // Title (exact: black, 16sp, bold, center, marginTop 8dp)
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
