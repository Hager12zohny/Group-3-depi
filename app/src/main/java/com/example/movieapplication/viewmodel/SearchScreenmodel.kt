package com.example.movieapplication.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapplication.model.Movie
import com.example.movieapplication.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchViewModel : ViewModel() {

    private val _searchResults = MutableStateFlow<List<Movie>>(emptyList())
    val searchResults: StateFlow<List<Movie>> = _searchResults

    private val _allMovies = MutableStateFlow<List<Movie>>(emptyList())
    val allMovies: StateFlow<List<Movie>> = _allMovies

    fun fetchAllMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val all = MovieRepository.getAllMovies()
                _allMovies.value = all
            } catch (e: Exception) {
                e.printStackTrace()
                _allMovies.value = emptyList()
            }
        }
    }

    fun searchMovies(query: String) {
        if (query.isBlank()) {
            _searchResults.value = emptyList()
            return
        }

        Log.d("SEARCH_VM", "Searching for movies with query: $query")

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val results = MovieRepository.searchMovies(query)
                withContext(Dispatchers.Main) {
                    _searchResults.value = results
                }
                Log.d("SEARCH_VM", "Fetched ${results.size} search results successfully.")
            } catch (e: Exception) {
                Log.e("SEARCH_VM", "Error searching movies: ${e.message}", e)
                withContext(Dispatchers.Main) {
                    _searchResults.value = emptyList()
                }
            }
        }
    }
}
