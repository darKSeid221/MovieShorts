package com.byteberserker.movieshorts.domain.usecase

import com.byteberserker.movieshorts.domain.model.Movie
import com.byteberserker.movieshorts.domain.repository.MovieRepository
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(query: String, page: Int = 1): List<Movie> {
        return if (query.length >= 2) {
            repository.searchMovies(query, page)
        } else {
            emptyList()
        }
    }
}
