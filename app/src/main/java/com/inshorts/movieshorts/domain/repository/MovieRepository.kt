package com.inshorts.movieshorts.domain.repository

import com.inshorts.movieshorts.domain.model.Movie

interface MovieRepository {

    suspend fun getNowPlayingPreview(limit: Int = 6): List<Movie>
    suspend fun getTrendingPreview(limit: Int = 6): List<Movie>
}