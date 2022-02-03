package com.prathamesh.jetpack_compose_paging.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.prathamesh.jetpack_compose_paging.movie.Repository
import com.prathamesh.jetpack_compose_paging.models.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: Repository
) : ViewModel() {
    private val _loading = mutableStateOf(false)
    val loading: State<Boolean>
        get() = _loading
    val moviePagingFlow: Flow<PagingData<Movie>> = movieRepository.movies().cachedIn(viewModelScope)

}