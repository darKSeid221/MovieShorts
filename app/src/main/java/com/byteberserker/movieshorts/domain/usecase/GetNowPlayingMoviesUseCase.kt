package com.byteberserker.movieshorts.domain.usecase

import com.byteberserker.movieshorts.domain.model.Movie
import com.byteberserker.movieshorts.domain.repository.MovieRepository
import javax.inject.Inject

class GetNowPlayingMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(page: Int): List<Movie> {
        return repository.getNowPlayingMovies(page)
    }
}
