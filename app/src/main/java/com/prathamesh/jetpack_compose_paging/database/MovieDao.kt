package com.prathamesh.jetpack_compose_paging.database

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.prathamesh.jetpack_compose_paging.models.Movie

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movie: List<Movie>)

    @Query("SELECT * FROM movie")
    fun pagedTopRated(): PagingSource<Int,Movie>

    @Query("DELETE FROM movie")
    fun clearMovies()
}