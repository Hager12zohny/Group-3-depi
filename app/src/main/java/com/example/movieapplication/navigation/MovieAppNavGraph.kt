package com.example.movieapplication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieapplication.screens.DetailsScreen
import com.example.movieapplication.screens.MovieHomeScreen
import com.example.movieapplication.viewmodel.HomeViewModel

@Composable
fun MovieAppNavGraph(homeViewModel: HomeViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {

        composable("home") {
            MovieHomeScreen(
                homeViewModel = homeViewModel,
                onMovieClick = { movieId ->
                    navController.navigate("details/$movieId")
                }
            )
        }

        composable(
            route = "details/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId") ?: 0
            DetailsScreen(movieId = movieId)
        }
    }
}

