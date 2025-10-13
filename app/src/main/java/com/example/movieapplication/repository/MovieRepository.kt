package com.example.movieapplication.repository

import com.example.movieapplication.model.Genre
import com.example.movieapplication.model.Movie
import com.example.movieapplication.model.MovieDetails
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

object MovieRepository {

   // api key
    private const val API_KEY = "0e190f11fcdfb1b31cc1f977bb9420f5"
    private const val BASE_URL = "https://api.themoviedb.org/3"
    private const val LANG = "en-US"

    // function to get http request
    private fun makeHttpRequest(urlString: String): String? {
        var conn: HttpURLConnection? = null
        return try {
            conn = (URL(urlString).openConnection() as HttpURLConnection).apply {
                requestMethod = "GET"
                connectTimeout = 10_000
                readTimeout = 10_000
            }
            BufferedReader(InputStreamReader(conn.inputStream)).use { it.readText() }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } finally {
            conn?.disconnect()
        }
    }

    // function fetch movies to get their details
    private fun fetchMovieList(urlString: String): List<Movie> {
        val jsonResponse = makeHttpRequest(urlString) ?: return emptyList()
        val jsonObject = JSONObject(jsonResponse)
        val resultsArray = jsonObject.getJSONArray("results")

        val movies = mutableListOf<Movie>()
        for (i in 0 until resultsArray.length()) {
            val item = resultsArray.getJSONObject(i)
            movies.add(
                Movie(
                    id = item.getInt("id"),
                    title = item.optString("title", ""),
                    poster_path = item.optString("poster_path", ""),
                    vote_average = item.optDouble("vote_average", 0.0),
                    release_date = item.optString("release_date", ""),
                    overview = item.optString("overview", "")
                )
            )
        }
        return movies
    }
    //for homescreen
    fun getTrendingMovies(timeWindow: String = "day"): List<Movie> {
        val url = "$BASE_URL/trending/movie/$timeWindow?api_key=$API_KEY&language=$LANG"
        return fetchMovieList(url)
    }
    //for homescreen
    fun getNowPlayingMovies(): List<Movie> {
        val url = "$BASE_URL/movie/now_playing?api_key=$API_KEY&language=$LANG&page=1"
        return fetchMovieList(url)
    }
    //search movies( for search screen)
    fun searchMovies(query: String): List<Movie> {
        val encodedQuery = java.net.URLEncoder.encode(query, "UTF-8")
        val url = "$BASE_URL/search/movie?api_key=$API_KEY&language=$LANG&query=$encodedQuery&page=1"
        return fetchMovieList(url)
    }
    //for detail screen
    fun getMovieDetails(movieId: Int): MovieDetails? {
        val url = "$BASE_URL/movie/$movieId?api_key=$API_KEY&language=$LANG"
        val jsonResponse = makeHttpRequest(url) ?: return null
        val item = JSONObject(jsonResponse)

        val genres = mutableListOf<Genre>()
        val genresArray = item.optJSONArray("genres")
        if (genresArray != null) {
            for (i in 0 until genresArray.length()) {
                val genreObj = genresArray.getJSONObject(i)
                genres.add(
                    Genre(
                        id = genreObj.getInt("id"),
                        name = genreObj.getString("name")
                    )
                )
            }
        }

        return MovieDetails(
            id = item.getInt("id"),
            title = item.optString("title", ""),
            overview = item.optString("overview", ""),
            poster_path = item.optString("poster_path", ""),
            release_date = item.optString("release_date", ""),
            vote_average = item.optDouble("vote_average", 0.0),
            runtime = item.optInt("runtime", 0),
            genres = genres
        )
    }
}



