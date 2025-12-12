package com.byteberserker.movieshorts.data.remote.api

import com.byteberserker.movieshorts.data.remote.dto.NowPlayingMovieResponse
import com.byteberserker.movieshorts.data.remote.dto.SearchMovieResponse
import com.byteberserker.movieshorts.data.remote.dto.TrendingMovieResponse
import com.byteberserker.movieshorts.data.remote.dto.MovieDetailDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBAPIService {

    @GET("movie/now_playing")
    suspend fun getNowPlaying(@Query("api_key") apiKey: String, @Query("page")
    page: Int): NowPlayingMovieResponse
    
    @GET("trending/movie/day")
    suspend fun getTrending(@Query("api_key") apiKey: String, @Query("page")
    page: Int): TrendingMovieResponse
    
    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("page") page: Int = 1
    ): SearchMovieResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): MovieDetailDto
}