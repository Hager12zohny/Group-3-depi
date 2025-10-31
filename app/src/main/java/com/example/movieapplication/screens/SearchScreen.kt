import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
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

    // list of all movies
    val allMovies = listOf(
        Movie("Harry Potter", R.drawable.harry_potter.takeIf { true } ?: fallbackRes, 150.dp),
        Movie("Interstellar", R.drawable.interstellar.takeIf { true } ?: fallbackRes, 150.dp),
        Movie("The Matrix", R.drawable.the_matrix.takeIf { true } ?: fallbackRes, 150.dp),
        Movie("Titanic", R.drawable.titanic_poster.takeIf { true } ?: fallbackRes, 150.dp),
        Movie("The Maze Runner", R.drawable.the_maze_runner.takeIf { true } ?: fallbackRes, 150.dp),
        Movie("xXx", R.drawable.xxx_poster.takeIf { true } ?: fallbackRes, 150.dp),
        Movie("Inception", R.drawable.inception_poster.takeIf { true } ?: fallbackRes, 150.dp),
        Movie("Avatar", R.drawable.avatar.takeIf { true } ?: fallbackRes, 150.dp)
    )

    // Filtered movies
    val filteredMovies = allMovies.filter { movie ->
        movie.title.contains(searchQuery.text, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF4A235A))
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
            placeholder = {
                Text(
                    "Search here...",
                    color = Color.White.copy(alpha = 0.7f)
                )
            },
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
                unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                cursorColor = Color.White
            )
        )

        // Headline
        Text(
            text = "All Movies",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        // Grid Layout
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            contentPadding = PaddingValues(4.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filteredMovies) { movie ->
                MovieCard(movie = movie)
            }
        }
    }
}

// Movie data class
data class Movie(val title: String, val posterRes: Int, val imageHeight: Dp)

@Composable
fun MovieCard(movie: Movie) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.7f), // Adjust aspect ratio for poster-like display
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                painter = painterResource(id = movie.posterRes),
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // Image takes most space
                    .clip(RoundedCornerShape(4.dp))
            )

            // Title
            Text(
                text = movie.title.trim(),
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MovieScreenPreview() {
    MovieScreen()
}