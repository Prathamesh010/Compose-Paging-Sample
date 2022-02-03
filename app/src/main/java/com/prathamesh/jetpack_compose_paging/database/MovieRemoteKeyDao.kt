package com.prathamesh.jetpack_compose_paging.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.prathamesh.jetpack_compose_paging.models.MovieKey

@Dao
interface MovieRemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<MovieKey>)

    @Query("SELECT * FROM moviekey WHERE movieId = :repoId")
    suspend fun remoteKeysMovieId(repoId: String): MovieKey?

    @Query("DELETE FROM moviekey")
    suspend fun clearRemoteKeys()
}
