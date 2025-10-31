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

grid/search/marocika
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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.movieapplication.viewmodel.SearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavHostController, viewModel: SearchViewModel = viewModel()) {

    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val searchResults = viewModel.searchResults.collectAsState().value
 main

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF4A235A))
 grid/search/marocika
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

            .padding(16.dp)
    ) {

        OutlinedTextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                viewModel.searchMovies(searchQuery.text) // << البحث الحقيقي هنا
            },
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.medium),
            placeholder = {
                Text("Search movies...", color = Color.White.copy(alpha = 0.7f))
            },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = null, tint = Color.White)
            },
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

        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(searchResults) { movie ->
                MovieItem(movie.title, movie.poster_path)
            }
        }
    }
}

@Composable
fun MovieItem(title: String, posterPath: String) {
    val imageUrl = "https://image.tmdb.org/t/p/w500$posterPath"

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .background(Color.White.copy(alpha = 0.15f))
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        AsyncImage(
            model = imageUrl,
            contentDescription = title,
            modifier = Modifier
                .size(80.dp)
                .clip(MaterialTheme.shapes.small)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = title,
            color = Color.White,
            style = MaterialTheme.typography.titleMedium
        )
    }
}
main
