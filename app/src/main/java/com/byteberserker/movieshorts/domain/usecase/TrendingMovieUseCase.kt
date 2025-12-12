package com.byteberserker.movieshorts.domain.usecase

import com.byteberserker.movieshorts.domain.repository.MovieRepository
import javax.inject.Inject

class TrendingMovieUseCase @Inject constructor(private val repository: MovieRepository) {
    suspend operator fun invoke() = repository.getTrendingPreview()
}