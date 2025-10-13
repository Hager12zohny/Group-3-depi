package com.example.movieapplication.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import com.example.movieapplication.R


@Composable
fun MovieDetailTop() {
    val purpleColor = Color(0xFF9C27B0)

    Column(
        modifier = Modifier
            .fillMaxSize()
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
            Image(
                painter = painterResource(id = R.drawable.movie_posterr),
                contentDescription = "Movie Poster",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Dune: Part Two",
            style = MaterialTheme.typography.headlineMedium.copy(
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(5) { index ->
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Star",
                    tint = if (index < 4) purpleColor else Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "4.0/5",
                color = Color.Gray,
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Action • Adventure • Sci-Fi",
            color = Color(0xFFAAAAAA),
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Duration: 2h 46m",
            color = Color(0xFFAAAAAA),
            fontSize = 13.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Paul Atreides unites with the Fremen people while seeking revenge against those who destroyed his family. As he navigates the political turmoil of Arrakis, he must face the destiny he never asked for.",
            color = Color(0xFFBBBBBB),
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 12.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(
                containerColor = purpleColor,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .height(48.dp)
                .width(200.dp)
        ) {
            Text(
                text = "🎬 Watch Trailer",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}


@Composable
fun MovieDetailBottom() {
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

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            CastItem(name = "Timothée Chalamet", imageRes = R.drawable.timotheee)
            CastItem(name = "Rebecca Ferguson", imageRes = R.drawable.rebeccaa)
            CastItem(name = "Zendaya", imageRes = R.drawable.zendayaa)
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

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ReviewItem(
                reviewer = "Ahmed Salah",
                content = "A masterpiece! The visuals and sound were stunning, and the story kept me hooked."
            )
            ReviewItem(
                reviewer = "Sara Khaled",
                content = "Even better than part one. Great performances and strong plot."
            )
        }
    }
}


@Composable
fun CastItem(name: String, imageRes: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
            .background(Color(0xFF2A2A3D), RoundedCornerShape(8.dp))
            .padding(12.dp)
    ) {
        Text(
            text = reviewer,
            color = Color(0xFFE0E0E0),
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = content,
            color = Color(0xFFCCCCCC),
            fontSize = 13.sp
        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MovieDetailTopPreview() {
    MovieDetailTop()
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MovieDetailBottomPreview() {
    MovieDetailBottom()
}
