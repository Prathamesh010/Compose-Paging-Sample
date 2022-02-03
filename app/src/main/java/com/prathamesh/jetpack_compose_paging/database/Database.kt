package com.prathamesh.jetpack_compose_paging.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.prathamesh.jetpack_compose_paging.models.Movie
import com.prathamesh.jetpack_compose_paging.models.MovieKey

@Database(entities = [Movie::class, MovieKey::class],version = 2,exportSchema = false)
abstract class Database: RoomDatabase(){
    abstract fun movieDao(): MovieDao
    abstract fun movieKeyDao(): MovieRemoteKeyDao

    companion object{
        const val DATABASE_NAME = "movie_db"
    }
}