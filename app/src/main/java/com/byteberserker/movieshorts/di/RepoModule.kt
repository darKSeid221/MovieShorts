package com.byteberserker.movieshorts.di

import com.byteberserker.movieshorts.data.local.dao.BookmarkDao
import com.byteberserker.movieshorts.data.local.dao.MovieDao
import com.byteberserker.movieshorts.data.remote.api.TMDBAPIService
import com.byteberserker.movieshorts.data.repository.MovieRepositoryImpl
import com.byteberserker.movieshorts.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class RepoModule {
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