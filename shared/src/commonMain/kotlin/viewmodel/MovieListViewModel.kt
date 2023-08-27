package viewmodel

import api.TMDBApi
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import model.Movie

class MovieListViewModel: ViewModel() {
    private val tmdbApi: TMDBApi = TMDBApi()

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    init{
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            tmdbApi.getAllMovies("US")
                .flowOn(Dispatchers.Default)
                .catch { _uiState.update { _ -> UiState.Error("Api Failure: ${it.message}") } }
                .collect { _uiState.update { _ -> UiState.Data(it.data) } }
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