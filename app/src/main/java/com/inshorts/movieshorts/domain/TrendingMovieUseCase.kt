package com.inshorts.movieshorts.domain

import com.inshorts.movieshorts.domain.repository.MovieRepository
import javax.inject.Inject

class TrendingMovieUseCase @Inject constructor(private val repository: MovieRepository) {
    suspend operator fun invoke() = repository.getTrendingPreview()
}