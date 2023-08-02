package page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import viewmodel.MovieListViewModel

@Composable
fun MovieListPage(viewModel: MovieListViewModel) {
    val state = viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Movies") },
                backgroundColor = MaterialTheme.colors.primary, contentColor = Color.White,
                elevation = 10.dp
            )
        },
    ) {
        when(state.value){
            is MovieListViewModel.UiState.Loading ->{}
            is MovieListViewModel.UiState.Error ->{

            }
            is MovieListViewModel.UiState.Data ->{
                val movies = (state.value as MovieListViewModel.UiState.Data).movies
                LazyColumn(Modifier.fillMaxSize().padding(horizontal = 12.dp, vertical = 16.dp)) {
                    items(movies.size) {
                        movieRow(movies[it].title)
                    }
                }
            }
        }
    }
}

@Composable
fun movieRow(name: String) {
    Card(Modifier.fillMaxWidth().padding(vertical = 6.dp).clickable {  }) {
        Text(
            name,
            Modifier.padding(all = 16.dp)
        )
    }
}