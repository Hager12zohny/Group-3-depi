package com.example.movieapplication.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
            CastItem(name = "Timothée Chalamet", imageRes = R.drawable.timothee)
            CastItem(name = "Rebecca Ferguson", imageRes = R.drawable.rebecca)
            CastItem(name = "Zendaya", imageRes = R.drawable.zendaya)
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
fun MovieDetailBottomPreview() {
    MovieDetailBottom()
}

