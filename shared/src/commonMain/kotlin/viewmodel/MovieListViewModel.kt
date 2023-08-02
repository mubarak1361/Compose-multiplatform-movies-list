package viewmodel

import api.TMDBApi
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import model.Movie

class MovieListViewModel: ViewModel() {
    private val tmdbApi: TMDBApi = TMDBApi()

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            try {
                val movies = tmdbApi.getAllMovies("US")
                _uiState.update { UiState.Data(movies.data) }
            } catch (ex: Exception) {
                _uiState.update { UiState.Error("Api Failure") }
            }
        }
    }


    sealed interface UiState {
        data class Data(
            val movies: List<Movie>
        ) : UiState

        data class Error(
            val errorMessage: String
        ) : UiState

        object Loading : UiState
    }
}