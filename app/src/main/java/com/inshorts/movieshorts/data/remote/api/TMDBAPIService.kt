package com.inshorts.movieshorts.data.remote.api

import com.inshorts.movieshorts.data.remote.dto.NowPlayingMovieResponse
import com.inshorts.movieshorts.data.remote.dto.TrendingMovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDBAPIService {

    @GET("movie/now_playing")
    suspend fun getNowPlaying(@Query("api_key") apiKey: String, @Query("page")
    page: Int): NowPlayingMovieResponse
    @GET("trending/movie/day")
    suspend fun getTrending(@Query("api_key") apiKey: String, @Query("page")
    page: Int): TrendingMovieResponse
}