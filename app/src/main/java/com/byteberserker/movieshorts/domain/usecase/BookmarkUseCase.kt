package com.byteberserker.movieshorts.domain.usecase

import com.byteberserker.movieshorts.domain.model.Movie
import com.byteberserker.movieshorts.domain.repository.MovieRepository
import javax.inject.Inject

class BookmarkUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend fun addBookmark(movie: Movie) {
        repository.addBookmark(movie)
    }

    suspend fun removeBookmark(movieId: Int) {
        repository.removeBookmark(movieId)
    }

    suspend fun getAllBookmarks(): List<Movie> {
        return repository.getAllBookmarks()
    }

    suspend fun isMovieBookmarked(movieId: Int): Boolean {
        return repository.isMovieBookmarked(movieId)
    }

    suspend fun toggleBookmark(movie: Movie) {
        if (isMovieBookmarked(movie.id)) {
            removeBookmark(movie.id)
        } else {
            addBookmark(movie)
        }
    }
}
