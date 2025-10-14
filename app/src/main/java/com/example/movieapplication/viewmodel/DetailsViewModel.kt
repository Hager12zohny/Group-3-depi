package com.example.movieapplication.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapplication.model.MovieDetails
import com.example.movieapplication.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailsViewModel : ViewModel() {

    private val _movieDetails = MutableStateFlow<MovieDetails?>(null)
    val movieDetails: StateFlow<MovieDetails?> = _movieDetails

    fun getMovieDetails(movieId: Int) {
        Log.d("DETAILS_VM", "🚀 Fetching movie details for ID = $movieId")

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val details = MovieRepository.getMovieDetails(movieId)
                if (details != null) {
                    Log.d("DETAILS_VM", "✅ Successfully fetched details for: ${details.title}")
                    withContext(Dispatchers.Main) {
                        _movieDetails.value = details
                    }
                } else {
                    Log.e("DETAILS_VM", "❌ No details returned for movieId = $movieId")
                }
            } catch (e: Exception) {
                Log.e("DETAILS_VM", "💥 Exception while fetching details: ${e.message}", e)
            }
        }
    }
}
