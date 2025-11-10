package com.example.movieapplication

//import androidx.xr.runtime.Config
import com.example.movieapplication.model.*
import com.example.movieapplication.repository.MovieRepository
import com.example.movieapplication.viewmodel.DetailsViewModel
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockkObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class DetailsViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var viewModel: DetailsViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        mockkObject(MovieRepository)
        viewModel = DetailsViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getMovieDetails returns movie, cast, and reviews`() = runTest {
        val fakeMovie = MovieDetails(
            id = 550,
            title = "Fight Club",
            overview = "A movie about ...",
            poster_path = "/poster.jpg",
            release_date = "1999-10-15",
            vote_average = 8.8,
            runtime = 139,
            genres = listOf(Genre(18, "Drama"))
        )

        val fakeCast = listOf(CastMember(1, "Brad Pitt", "Tyler Durden", "/brad.jpg"))
        val fakeReviews = listOf(Review("1", "User", "Great movie!", null))

        coEvery { MovieRepository.getMovieDetails(550) } returns fakeMovie
        coEvery { MovieRepository.getMovieCast(550) } returns fakeCast
        coEvery { MovieRepository.getMovieReviews(550) } returns fakeReviews

        viewModel.getMovieDetails(550)

        assertThat(viewModel.movieDetails.first()?.title).isEqualTo("Fight Club")
        assertThat(viewModel.castList.first()).isEqualTo(fakeCast)
        assertThat(viewModel.reviewList.first()).isEqualTo(fakeReviews)
        assertThat(viewModel.errorMessage.first()).isNull()
    }

    @Test
    fun `getMovieDetails sets errorMessage if movie not found`() = runTest {
        coEvery { MovieRepository.getMovieDetails(-1) } returns null
        coEvery { MovieRepository.getMovieCast(-1) } returns emptyList()
        coEvery { MovieRepository.getMovieReviews(-1) } returns emptyList()

        viewModel.getMovieDetails(-1)

        assertThat(viewModel.movieDetails.first()).isNull()
        assertThat(viewModel.errorMessage.first()).contains("Error")
    }
}
