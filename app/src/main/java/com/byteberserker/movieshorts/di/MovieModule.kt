package com.byteberserker.movieshorts.di

import android.content.Context
import androidx.room.Room
import com.byteberserker.movieshorts.BuildConfig
import com.byteberserker.movieshorts.data.local.AppDatabase
import com.byteberserker.movieshorts.data.local.dao.BookmarkDao
import com.byteberserker.movieshorts.data.local.dao.MovieDao
import com.byteberserker.movieshorts.data.remote.api.TMDBAPIService
import com.byteberserker.movieshorts.data.repository.MovieRepositoryImpl
import com.byteberserker.movieshorts.domain.repository.MovieRepository
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
    fun provideOkHttpClient(): okhttp3.OkHttpClient {
        return okhttp3.OkHttpClient.Builder()
            .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .readTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .writeTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .addInterceptor(okhttp3.logging.HttpLoggingInterceptor().apply {
                level = okhttp3.logging.HttpLoggingInterceptor.Level.BASIC
            })
            // Fix IPv6 connectivity issue - prefer IPv4
            .dns(object : okhttp3.Dns {
                override fun lookup(hostname: String): List<java.net.InetAddress> {
                    return try {
                        val addresses = java.net.InetAddress.getAllByName(hostname).toList()
                        // Prefer IPv4 addresses
                        addresses.sortedBy { it is java.net.Inet6Address }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        okhttp3.Dns.SYSTEM.lookup(hostname)
                    }
                }
            })
            .build()
    }

    @Provides
    @Singleton
    fun getRetrofit(okHttpClient: okhttp3.OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(getBaseUrl())
            .client(okHttpClient)
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
    fun provideBookmarkDao(db: AppDatabase): BookmarkDao = db.bookmarkDao()

    @Provides
    fun getMovieRepository(
        apiService: TMDBAPIService,
        @Named("TMDB_API_KEY") apiKey: String,
        movieDao: MovieDao,
        bookmarkDao: BookmarkDao
    ): MovieRepository {
        return MovieRepositoryImpl(apiService, movieDao, bookmarkDao, apiKey)
    }
}