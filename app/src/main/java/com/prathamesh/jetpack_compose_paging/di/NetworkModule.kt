package com.prathamesh.jetpack_compose_paging.di

import com.prathamesh.jetpack_compose_paging.network.ApiService
import com.prathamesh.jetpack_compose_paging.network.NetworkInterceptor
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule{

    @Singleton
    @Provides
    fun providesOkHttp(): OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(NetworkInterceptor())
            .build()
    }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi{
        return Moshi.Builder().build()
    }

    @Singleton
    @Provides
    fun providesRetrofit(moshi: Moshi, client: OkHttpClient): Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://api.instantwebtools.net/v1/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiClient(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}