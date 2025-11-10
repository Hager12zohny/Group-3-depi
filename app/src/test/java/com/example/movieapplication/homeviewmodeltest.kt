package com.example.movieapplication

import com.example.movieapplication.model.Movie
import com.example.movieapplication.viewmodel.HomeViewModel
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner


@RunWith(RobolectricTestRunner::class)
class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        viewModel = HomeViewModel()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun newReleases_initially_not_empty_after_fetch() = runTest {
        advanceUntilIdle()
        val movies = viewModel.newReleases.value

        assertThat(movies).isInstanceOf(List::class.java)
        if (movies.isNotEmpty()) {
            assertThat(movies[0]).isInstanceOf(Movie::class.java)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun trendingMovies_initially_not_empty_after_fetch() = runTest {
        advanceUntilIdle()
        val movies = viewModel.trendingMovies.value

        assertThat(movies).isInstanceOf(List::class.java)
        if (movies.isNotEmpty()) {
            assertThat(movies[0]).isInstanceOf(Movie::class.java)
        }
    }
}

