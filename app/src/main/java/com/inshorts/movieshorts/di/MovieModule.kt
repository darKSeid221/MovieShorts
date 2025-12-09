package com.inshorts.movieshorts.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.inshorts.movieshorts.BuildConfig
import com.inshorts.movieshorts.data.local.AppDatabase
import com.inshorts.movieshorts.data.local.dao.MovieDao
import com.inshorts.movieshorts.data.remote.api.TMDBAPIService
import com.inshorts.movieshorts.data.repository.MovieRepositoryImpl
import com.inshorts.movieshorts.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class MovieModule {
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

    @Provides
    @Singleton
    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context, AppDatabase::class.java,
            "movies_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideMovieDao(db: AppDatabase): MovieDao = db.movieDao()

    @Provides
    fun getMovieRepository(
        apiService: TMDBAPIService,
        @Named("TMDB_API_KEY") apiKey: String,
        movieDao: MovieDao
    ): MovieRepository {
        return MovieRepositoryImpl(apiService, movieDao, apiKey)
    }
}