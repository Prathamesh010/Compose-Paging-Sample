package com.prathamesh.jetpack_compose_paging.di

import android.content.Context
import androidx.room.Room
import com.prathamesh.jetpack_compose_paging.database.Database
import com.prathamesh.jetpack_compose_paging.database.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): Database {
        return Room.databaseBuilder(
            appContext,
            Database::class.java,
            Database.DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideMovieDao(database: Database): MovieDao {
        return database.movieDao()
    }
}