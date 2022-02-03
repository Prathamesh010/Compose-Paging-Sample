package com.prathamesh.jetpack_compose_paging.network

import com.prathamesh.jetpack_compose_paging.models.Movie
import com.prathamesh.jetpack_compose_paging.models.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("passenger")
    suspend fun getTopRatedMovies(@Query("page")page: Int,@Query("size")size: Int = 10): MovieResponse
}