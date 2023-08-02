import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import page.MovieListPage
import viewmodel.MovieListViewModel

@Composable
fun App() {
    MaterialTheme {
        val moviesViewModel = getViewModel(Unit, viewModelFactory { MovieListViewModel() })
        MovieListPage(moviesViewModel)
    }
}

expect fun getPlatformName(): String