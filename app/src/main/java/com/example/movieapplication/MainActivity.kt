package com.example.movieapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.movieapplication.screens.DetailsScreen
import com.example.movieapplication.ui.theme.MovieApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        Log.d("DETAILS_VM", "MainActivity started")

        setContent {
            MovieApplicationTheme {

                DetailsScreen(movieId = 550)
            }
        }
    }
}
