package com.byteberserker.movieshorts.domain.repository

import com.byteberserker.movieshorts.domain.model.Movie

interface MovieRepository {

    suspend fun getNowPlayingPreview(limit: Int = 6): List<Movie>
    suspend fun getTrendingPreview(limit: Int = 6): List<Movie>
    
    suspend fun getTrendingMovies(page: Int): List<Movie>
    suspend fun getNowPlayingMovies(page: Int): List<Movie>
    
    suspend fun searchMovies(query: String, page: Int = 1): List<Movie>
    
    suspend fun addBookmark(movie: Movie)
    suspend fun removeBookmark(movieId: Int)
    suspend fun getAllBookmarks(): List<Movie>
    suspend fun isMovieBookmarked(movieId: Int): Boolean
    
    suspend fun getMovieDetails(movieId: Int): Movie?
}