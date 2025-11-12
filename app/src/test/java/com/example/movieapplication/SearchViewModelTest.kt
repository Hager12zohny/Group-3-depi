package com.example.movieapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapplication.model.Movie
import com.example.movieapplication.repository.MovieRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner


data class UiState(
    val movies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class SearchViewModel(private val repo: MovieRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    fun searchMovies(query: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val movies = repo.searchMovies(query)

                _uiState.value = _uiState.value.copy(
                    movies = movies,
                    isLoading = false,
                    errorMessage = null
                )

                _uiState.value =
                    _uiState.value.copy(movies = movies, isLoading = false, errorMessage = null)

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message
                )
            }
        }
    }
}


@RunWith(RobolectricTestRunner::class)
class SearchViewModelTest {

    private val repo = mockk<MovieRepository>()
    private lateinit var viewModel: SearchViewModel
    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        viewModel = SearchViewModel(repo)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun searchQueryReturnsMovieList() = runTest {
        val query = "avatar"
        val fakeMovies = listOf(Movie(5, "Avatar", "poster.png", 8.0, "Sci-fi"))
        coEvery { repo.searchMovies(query) } returns fakeMovies

        viewModel.searchMovies(query)
        advanceUntilIdle()

        assertThat(viewModel.uiState.value.movies).isEqualTo(fakeMovies)
        assertThat(viewModel.uiState.value.isLoading).isFalse()
    }

    @Test
    fun searchQueryReturnsEmptyList() = runTest {
        coEvery { repo.searchMovies("nothing") } returns emptyList()

        viewModel.searchMovies("nothing")
        advanceUntilIdle()

        assertThat(viewModel.uiState.value.movies).isEmpty()
        assertThat(viewModel.uiState.value.isLoading).isFalse()
    }

    @Test
    fun searchQueryFailsGracefully() = runTest {
        coEvery { repo.searchMovies(any()) } throws RuntimeException("API failed")

        viewModel.searchMovies("error")
        advanceUntilIdle()

        assertThat(viewModel.uiState.value.errorMessage).contains("API failed")
        assertThat(viewModel.uiState.value.movies).isEmpty()
    }
}
