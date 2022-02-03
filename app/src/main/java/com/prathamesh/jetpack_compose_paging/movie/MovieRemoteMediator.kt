package com.prathamesh.jetpack_compose_paging.movie

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.prathamesh.jetpack_compose_paging.database.Database
import com.prathamesh.jetpack_compose_paging.models.Movie
import com.prathamesh.jetpack_compose_paging.models.MovieKey
import com.prathamesh.jetpack_compose_paging.models.MovieResponse
import com.prathamesh.jetpack_compose_paging.network.ApiService
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class MovieRemoteMediator(
    private val database: Database,
    private val networkService: ApiService
) : RemoteMediator<Int, Movie>() {
    override suspend fun load(loadType: LoadType, state: PagingState<Int, Movie>): MediatorResult {
        val page:Int = when(loadType){
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }
            LoadType.PREPEND ->  {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }
        return try {
            val response
                    : MovieResponse = networkService.getTopRatedMovies(page)
            val endOfPaginationReached = page + 1 > response.totalPages
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.movieKeyDao().clearRemoteKeys()
                    database.movieDao().clearMovies()
                }
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = response.data.map{
                    MovieKey(movieId = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                database.movieDao().insertMovies(response.data)
                database.movieKeyDao().insertAll(keys)
            }
            MediatorResult.Success(
                endOfPaginationReached = endOfPaginationReached
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Movie>): MovieKey? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { repo ->
                // Get the remote keys of the last item retrieved
                database.movieKeyDao().remoteKeysMovieId(repo.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Movie>): MovieKey? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { repo ->
                // Get the remote keys of the first items retrieved
                database.movieKeyDao().remoteKeysMovieId(repo.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Movie>
    ): MovieKey? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                database.movieKeyDao().remoteKeysMovieId(repoId)
            }
        }
    }
}