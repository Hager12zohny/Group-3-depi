package com.example.movieapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.movieapplication.ui.theme.MovieApplicationTheme
import com.example.movieapplication.viewmodel.HomeViewModel
import com.example.movieapplication.navigation.MovieAppNavGraph

class MainActivity : ComponentActivity() {


    private val homeViewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieApplicationTheme {
                MovieAppNavGraph(homeViewModel = homeViewModel)
            }
        }
    }
}
