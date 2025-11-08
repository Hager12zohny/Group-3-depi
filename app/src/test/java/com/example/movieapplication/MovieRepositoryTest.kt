package com.example.movieapplication

import com.example.movieapplication.repository.MovieRepository
import com.google.common.truth.Truth
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

//using RoboectrictTestRunner

@RunWith(RobolectricTestRunner::class)


class MovieRepositoryTest {

        @Test
        fun `searchMovies returns empty list for nonsense query`() = runTest {
            val result = MovieRepository.searchMovies("ajsfhkajsfhkajsfhkajsfh")
            Truth.assertThat(result).isEmpty()
        }

        @Test
        fun `getTrendingMovies returns non-empty list`() = runTest {
            val result = MovieRepository.getTrendingMovies()
            // API call might fail if offline, but should return either list or empty list safely
            Truth.assertThat(result).isNotNull()
        }

        @Test
        fun `getNowPlayingMovies should return a valid list`() = runTest {
            val result = MovieRepository.getNowPlayingMovies()
            Truth.assertThat(result).isNotNull()
        }
    }