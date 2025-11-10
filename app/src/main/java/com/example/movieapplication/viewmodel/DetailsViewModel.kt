package com.example.movieapplication.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapplication.model.MovieDetails
import com.example.movieapplication.model.CastMember
import com.example.movieapplication.model.Review
import com.example.movieapplication.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailsViewModel : ViewModel() {

    private val _movieDetails = MutableStateFlow<MovieDetails?>(null)
    val movieDetails: StateFlow<MovieDetails?> = _movieDetails

    private val _castList = MutableStateFlow<List<CastMember>>(emptyList())
    val castList: StateFlow<List<CastMember>> = _castList

    private val _reviewList = MutableStateFlow<List<Review>>(emptyList())
    val reviewList: StateFlow<List<Review>> = _reviewList

    val errorMessage = MutableStateFlow<String?>(null)

    fun getMovieDetails(movieId: Int) {
        Log.d("DETAILS_VM", "Fetching movie details for ID = $movieId")


        viewModelScope.launch {
            try {
                val details = MovieRepository.getMovieDetails(movieId)
                val cast = MovieRepository.getMovieCast(movieId)
                val reviews = MovieRepository.getMovieReviews(movieId)

                if (details == null) {
                    errorMessage.value = "Error: Movie not found"
                    _movieDetails.value = null
                    _castList.value = emptyList()
                    _reviewList.value = emptyList()
                } else {
                    _movieDetails.value = details
                    _castList.value = cast
                    _reviewList.value = reviews
                    errorMessage.value = null
                }

                Log.d(
                    "DETAILS_VM",
                    "Fetched details, cast (${cast.size}), and reviews (${reviews.size}) successfully."
                )
            } catch (e: Exception) {
                errorMessage.value = "Error fetching movie data: ${e.message}"
                _movieDetails.value = null
                _castList.value = emptyList()
                _reviewList.value = emptyList()
                Log.e("DETAILS_VM", "Error fetching movie data: ${e.message}", e)
            }
        }
    }
}
