package com.example.movieapp.viewmodel

import com.example.movieapp.data.Movie
import com.example.movieapp.data.MovieRepository
import com.example.movieapplication.repository.MovieRepository
import com.example.movieapplication.viewmodel.HomeViewModel
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers

import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.*

@RunWith(RobolectricTestRunner::class)
class HomeViewModelTest {

    private val repo = mock<MovieRepository>()
    private lateinit var viewModel: HomeViewModel
    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        viewModel = HomeViewModel(repo)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun fetch popular movies updates uiState() = runTest {
        val fakeMovies = listOf(Movie(1, "Inception", "poster.jpg", 9.0, "Dream"))
        whenever(repo.getPopularMovies()).thenReturn(fakeMovies)

        viewModel.fetchPopularMovies()
        advanceUntilIdle()

        assertThat(viewModel.uiState.value.movies).isEqualTo(fakeMovies)
        assertThat(viewModel.uiState.value.isLoading).isFalse()
    }

    @Test
    fun fetch popular movies handles error() = runTest {
        whenever(repo.getPopularMovies()).thenThrow(RuntimeException("Network Error"))

        viewModel.fetchPopularMovies()
        advanceUntilIdle()

        assertThat(viewModel.uiState.value.errorMessage).contains("Network")
        assertThat(viewModel.uiState.value.movies).isEmpty()
    }
}