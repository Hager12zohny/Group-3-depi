package com.example.movieapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapplication.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class HomeViewModel : ViewModel() {

    private val apiKey = "0e190f11fcdfb1b31cc1f977bb9420f5"

    private val _newReleases = MutableStateFlow<List<Movie>>(emptyList())
    val newReleases: StateFlow<List<Movie>> = _newReleases

    private val _trending = MutableStateFlow<List<Movie>>(emptyList())
    val trendingMovies: StateFlow<List<Movie>> = _trending

    init {
        fetchMoviesHttp()
    }

    private fun fetchMoviesHttp() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val newReleasesList = fetchMoviesFromApi("movie/now_playing")
                val trendingList = fetchMoviesFromApi("trending/movie/day")

                _newReleases.value = newReleasesList
                _trending.value = trendingList
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun fetchMoviesFromApi(endpoint: String): List<Movie> {
        val url = URL("https://api.themoviedb.org/3/$endpoint?api_key=$apiKey")
        val connection = url.openConnection() as HttpURLConnection

        return try {
            connection.requestMethod = "GET"
            connection.connectTimeout = 10000
            connection.readTimeout = 10000

            val stream = connection.inputStream.bufferedReader().use { it.readText() }
            parseMoviesJson(stream)
        } finally {
            connection.disconnect()
        }
    }

    private fun parseMoviesJson(jsonString: String): List<Movie> {
        val jsonObject = JSONObject(jsonString)
        val results = jsonObject.getJSONArray("results")
        val movies = mutableListOf<Movie>()

        for (i in 0 until results.length()) {
            val item = results.getJSONObject(i)

            val posterUrl = if (item.has("poster_path") && !item.isNull("poster_path") && item.getString("poster_path").isNotEmpty()) {
                "https://image.tmdb.org/t/p/w500${item.getString("poster_path")}"
            } else {
                "https://via.placeholder.com/500x750.png?text=No+Image"
            }

            val overview = if (item.has("overview") && !item.isNull("overview")) {
                item.getString("overview")
            } else {
                "No description available"
            }

            val movie = Movie(
                id = item.getInt("id"),
                title = item.getString("title"),
                poster_path = posterUrl,
                overview = overview
            )
            movies.add(movie)
        }

        return movies
    }
}

