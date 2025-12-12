package com.byteberserker.movieshorts.di

import com.byteberserker.movieshorts.BuildConfig
import com.byteberserker.movieshorts.data.remote.api.TMDBAPIService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {
    @Provides
    @Named("MOVIE_BASE_URL")
    fun getBaseUrl() = "https://api.themoviedb.org/3/"

    @Provides
    @Named("TMDB_API_KEY")
    fun getAPIKey() = BuildConfig.TMDB_API_KEY

    @Provides
    @Singleton
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(getBaseUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun getApi(retrofit: Retrofit): TMDBAPIService {
        return retrofit.create(TMDBAPIService::class.java)
    }
}