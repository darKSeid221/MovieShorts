package com.byteberserker.movieshorts.domain.repository

import com.byteberserker.movieshorts.domain.model.Movie

interface MovieRepository {

    suspend fun getNowPlayingPreview(limit: Int = 6): List<Movie>
    suspend fun getTrendingPreview(limit: Int = 6): List<Movie>
    
    // Pagination methods
    suspend fun getTrendingMovies(page: Int): List<Movie>
    suspend fun getNowPlayingMovies(page: Int): List<Movie>
    
    // Search method
    suspend fun searchMovies(query: String, page: Int = 1): List<Movie>
    
    // Bookmark methods
    suspend fun addBookmark(movie: Movie)
    suspend fun removeBookmark(movieId: Int)
    suspend fun getAllBookmarks(): List<Movie>
    suspend fun isMovieBookmarked(movieId: Int): Boolean
}