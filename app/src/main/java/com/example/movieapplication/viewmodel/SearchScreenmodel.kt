package com.example.movieapplication.viewmodel 
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapplication.model.Movie 
import com.example.movieapplication.repository.MovieRepository  
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    //  hold the search results
    private val _searchResults = MutableStateFlow<List<Movie>>(emptyList())
    val searchResults: StateFlow<List<Movie>> = _searchResults

    //  the search 
    fun searchMovies(query: String) {
        viewModelScope.launch {
            try {
                val results = MovieRepository.searchMovies(query)  
                _searchResults.value = results  
            } catch (e: Exception) {
                // Handle errors 
                e.printStackTrace()  
                _searchResults.value = emptyList()  
            }
        }
    }
}
