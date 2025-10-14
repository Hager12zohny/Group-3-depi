package com.example.movieapplication.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.movieapplication.R
import com.example.movieapplication.viewmodel.DetailsViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun DetailsScreen(movieId: Int, viewModel: DetailsViewModel = viewModel()) {
    val movieDetailsState = viewModel.movieDetails.collectAsState()

    // ✅ مؤقتًا بنجهز ليست فاضية لحد ما الفريق يضيف الـ functions
    val castList = remember { mutableStateListOf<CastMember>() }
    val reviewList = remember { mutableStateListOf<Review>() }

    // تحميل بيانات الفيلم عند فتح الشاشة
    LaunchedEffect(movieId) {
        Log.d("DETAILS_UI", "Requesting details for movieId=$movieId")
        viewModel.getMovieDetails(movieId)
        // هنا بعدين هتستدعي:
        // castList.addAll(MovieRepository.getMovieCast(movieId))
        // reviewList.addAll(MovieRepository.getMovieReviews(movieId))
    }

    val movieDetails = movieDetailsState.value

    when {
        movieDetails == null -> {
            // حالة التحميل
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color(0xFF9C27B0))
            }
        }

        else -> {
            Log.d("DETAILS_UI", "Showing: ${movieDetails.title}")
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF1C1C27))
                    .verticalScroll(rememberScrollState())
            ) {
                MovieDetailTopDynamic(
                    title = movieDetails.title,
                    overview = movieDetails.overview,
                    posterPath = movieDetails.poster_path
                )
                // ✅ نمرر القوائم هنا
                MovieDetailBottom(cast = castList, reviews = reviewList)
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
            .padding(16.dp)
    ) {
        Text(
            text = "Cast",
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.height(12.dp))

        if (cast.isEmpty()) {
            Text("Loading cast...", color = Color.Gray, fontSize = 12.sp)
        } else {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                cast.take(3).forEach { member ->
                    CastItem(name = member.name, imageRes = R.drawable.timotheee)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Reviews",
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (reviews.isEmpty()) {
            Text("Loading reviews...", color = Color.Gray, fontSize = 12.sp)
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                reviews.take(2).forEach { review ->
                    ReviewItem(reviewer = review.author, content = review.content)
                }
            }
        }
    }
}

@Composable
fun CastItem(name: String, imageRes: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Card(
            shape = CircleShape,
            modifier = Modifier.size(60.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = name,
            color = Color.White,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
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

@Preview(showBackground = true)
@Composable
fun PreviewDetailsScreen() {
    DetailsScreen(movieId = 550)
}
