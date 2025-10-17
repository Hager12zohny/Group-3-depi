package com.example.movieapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapplication.model.Movie
import com.example.movieapplication.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val apiKey = "72cfd6fdf27b6a0abb9fdc22fb2ee866"

    private val _newReleases = MutableStateFlow<List<Movie>>(emptyList())
    val newReleases: StateFlow<List<Movie>> = _newReleases

    private val _popularMovies = MutableStateFlow<List<Movie>>(emptyList())
    val popularMovies: StateFlow<List<Movie>> = _popularMovies

    private val _trending = MutableStateFlow<List<Movie>>(emptyList())
    val trendingMovies: StateFlow<List<Movie>> = _trending

    init {
        fetchMovies()
    }

    private fun fetchMovies() {
        viewModelScope.launch {
            try {
                val newReleaseResponse = RetrofitClient.instance.getNewReleases(apiKey)
                val popularResponse = RetrofitClient.instance.getPopularMovies(apiKey)
                val trendingResponse = RetrofitClient.instance.getTrendingMovies(apiKey)

                _newReleases.value = newReleaseResponse.results
                _popularMovies.value = popularResponse.results
                _trending.value = trendingResponse.results
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

