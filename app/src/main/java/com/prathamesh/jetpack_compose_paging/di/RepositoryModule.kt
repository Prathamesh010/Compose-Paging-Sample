package com.prathamesh.jetpack_compose_paging.di

import com.prathamesh.jetpack_compose_paging.movie.Repository
import com.prathamesh.jetpack_compose_paging.database.Database
import com.prathamesh.jetpack_compose_paging.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule{

    @Provides
    @Singleton
    fun provideRepository(
        database: Database,
        client: ApiService
    ): Repository{
        return Repository(database,client)
    }
}