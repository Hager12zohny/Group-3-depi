package com.example.movieapplication.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.movieapplication.model.CastMember
import com.example.movieapplication.model.Review
import com.example.movieapplication.viewmodel.DetailsViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun DetailsScreen(movieId: Int, viewModel: DetailsViewModel = viewModel()) {
    val movieDetails by viewModel.movieDetails.collectAsState()
    val castList by viewModel.castList.collectAsState()
    val reviewList by viewModel.reviewList.collectAsState()

    LaunchedEffect(movieId) {
        Log.d("DETAILS_UI", "Requesting details for movieId=$movieId")
        viewModel.getMovieDetails(movieId)
    }

    when {
        movieDetails == null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color(0xFF9C27B0))
            }
        }

        else -> {
            Log.d("DETAILS_UI", "Showing: ${movieDetails?.title}")
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF1C1C27))
                    .verticalScroll(rememberScrollState())
            ) {
                MovieDetailTopDynamic(
                    title = movieDetails?.title,
                    overview = movieDetails?.overview,
                    posterPath = movieDetails?.poster_path
                )

                MovieDetailBottom(
                    cast = castList,
                    reviews = reviewList
                )
            }
        }
    }
}

@Composable
fun MovieDetailTopDynamic(
    title: String?,
    overview: String?,
    posterPath: String?
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF1C1C27))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500${posterPath ?: ""}",
                contentDescription = title ?: "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = title ?: "",
            style = MaterialTheme.typography.headlineMedium.copy(
                color = Color.White,
                fontWeight = FontWeight.Bold
            ),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = overview ?: "",
            color = Color(0xFFBBBBBB),
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 12.dp)
        )
    }
}

@Composable
fun MovieDetailBottom(
    cast: List<CastMember> = emptyList(),
    reviews: List<Review> = emptyList()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1C1C27))
            .padding(horizontal = 16.dp)
    ) {
        // Cast Section
        Text(
            text = "Cast",
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color.White,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (cast.isEmpty()) {
            Text("Loading cast...", color = Color.Gray, fontSize = 12.sp)
        } else {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(cast.take(6)) { member ->
                    CastItem(
                        name = member.name ?: "Unknown",
                        imageUrl = member.profile_path
                    )
                }
            }
        }

        // Reviews Section
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Reviews",
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color.White,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(top = 4.dp)
        )

        if (reviews.isEmpty()) {
            Text("Loading reviews...", color = Color.Gray, fontSize = 12.sp)
        } else {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.padding(top = 6.dp)
            ) {
                reviews.take(3).forEach { review ->
                    ReviewItem(
                        reviewer = review.author ?: "Anonymous",
                        content = review.content ?: "No review available."
                    )
                }
            }
        }
    }
}

@Composable
fun CastItem(name: String, imageUrl: String?) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(80.dp)
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500$imageUrl",
            contentDescription = name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = name,
            color = Color.White,
            fontSize = 12.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun ReviewItem(reviewer: String, content: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF2C2C3A), RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Text(
            text = reviewer,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = content,
            color = Color(0xFFBBBBBB),
            fontSize = 13.sp
        )
    }
}
