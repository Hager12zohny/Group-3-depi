package com.example.movieapplication.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movieapplication.R

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieHomeScreen()
        }
    }
}

@Composable
fun MovieHomeScreen() {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color.Black,
                        Color.Black,
                        Color(0xFF9C27B0)
                    )
                )
            )
            .padding(8.dp)
    ) {
        // search bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .padding(8.dp),
            placeholder = { Text("Search movies...", color = Color(0xFF9C27B0)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color(0xFF9C27B0),
                    modifier = Modifier
                        .size(24.dp)
                        .shadow(
                            elevation = 24.dp,
                            ambientColor = Color(0xFF9C27B0),
                            spotColor = Color(0xFF9C27B0)
                        )
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF9C27B0),
                unfocusedBorderColor = Color(0xFF9C27B0),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        // banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.missionimpossible),
                contentDescription = "Featured Movie",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(32.dp))
            )

            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.LightGray.copy(alpha = 0.6f)
                ),
                modifier = Modifier.align(Alignment.Center)
            ) {
                Icon(
                    Icons.Default.PlayArrow,
                    contentDescription = null,
                    tint = Color(0xFF9C27B0)
                )
                Spacer(Modifier.width(2.dp))
                Text("Watch Now", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // divider to seperate between banner and cards section
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 7.dp),
            thickness = 1.dp,
            color = Color(0xFF9C27B0)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MovieHomeScreenPreview() {
    MovieHomeScreen()
}

