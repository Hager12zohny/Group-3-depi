package com.example.movieapp

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items

data class Cast(val name: String, val imageRes: Int)

@SuppressLint("UseKtx")
@Composable
fun MovieDetailScreen() {
    val purpleColor = Color(0xFF9C27B0)
    val context = LocalContext.current

    // استخدام الصور الحقيقية من مجلد drawable
    val castList = listOf(
        Cast("Timothée Chalamet", R.drawable.timothee_chalamet),
        Cast("Rebecca Ferguson", R.drawable.rebecca),
        Cast("Zendaya", R.drawable.zendaya)
    )

    val reviews = listOf(
        "Amazing visuals and storytelling! ⭐⭐⭐⭐",
        "Great acting, but a bit long. ⭐⭐⭐",
        "Epic continuation of Dune saga! ⭐⭐⭐⭐⭐"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1C1C27))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Poster - استخدام الصورة الحقيقية
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.movie_poster),
                contentDescription = "Movie Poster",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Movie Name
        Text(
            text = "Dune: Part Two",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Genre + Duration
        Text(
            text = "Action • Adventure • Sci-Fi • 2h 35m",
            color = Color(0xFFAAAAAA),
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Rating
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

        Spacer(modifier = Modifier.height(12.dp))

        // Description
        Text(
            text = "Paul Atreides unites with the Fremen while seeking revenge against those who destroyed his family.",
            color = Color(0xFFBBBBBB),
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 12.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Watch Trailer Button
        Button(
            onClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=example"))
                context.startActivity(intent)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = purpleColor
            ),
            modifier = Modifier
                .height(48.dp)
                .width(200.dp)
        ) {
            Text("🎬 Watch Trailer", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Cast Section - باستخدام الصور الحقيقية
        Text(
            text = "Cast",
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(castList) { cast ->
                CastItem(name = cast.name, imageRes = cast.imageRes)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Reviews Section
        Text(
            text = "Reviews",
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            reviews.forEach { review ->
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = review,
                        modifier = Modifier.padding(8.dp),
                        color = Color.Black
                    )
                }
            }
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
            modifier = Modifier.size(60.dp)
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

@Preview(showBackground = true)
@Composable
fun MovieDetailScreenPreview() {
    MovieDetailScreen()
}