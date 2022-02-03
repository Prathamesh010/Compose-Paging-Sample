package com.prathamesh.jetpack_compose_paging.movie

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.prathamesh.jetpack_compose_paging.database.Database
import com.prathamesh.jetpack_compose_paging.models.Movie
import com.prathamesh.jetpack_compose_paging.network.ApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class Repository @Inject constructor(
    val database: Database,
    private val apiService: ApiService
){
    @ExperimentalPagingApi
    fun movies(): Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(pageSize = 20),
        remoteMediator = MovieRemoteMediator(database,apiService),
    ){
        database.movieDao().pagedTopRated()
    }.flow
}