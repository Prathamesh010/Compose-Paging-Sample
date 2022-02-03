package com.prathamesh.jetpack_compose_paging.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.prathamesh.jetpack_compose_paging.models.Movie

@ExperimentalCoilApi
@ExperimentalPagingApi
@Composable
fun Main(
    viewModel: MovieViewModel = hiltViewModel()
){
    val movieList = viewModel.moviePagingFlow.collectAsLazyPagingItems()
    MainScreen(movieList)
}

@ExperimentalCoilApi
@ExperimentalPagingApi
@Composable
fun MainScreen(
    movieList: LazyPagingItems<Movie>
){
    LazyColumn{
        items(movieList){ movie ->
            if (movie != null) {
                Card(movie)
            }
        }
    }
}

@ExperimentalCoilApi
@Composable
fun Card(
    movie: Movie
){
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column {
            movie.name?.let { Text(it) }
            Text(movie.trips.toString())
        }
    }
}