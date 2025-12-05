package com.inshorts.movieshorts.data.repository

import com.inshorts.movieshorts.data.local.dao.MovieDao
import com.inshorts.movieshorts.data.local.entity.MovieEntity
import com.inshorts.movieshorts.data.remote.api.TMDBAPIService
import com.inshorts.movieshorts.data.remote.dto.ResultsItemNowPlaying
import com.inshorts.movieshorts.data.remote.dto.ResultsItemTrending
import com.inshorts.movieshorts.domain.model.Movie
import com.inshorts.movieshorts.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: TMDBAPIService,
    private val dao: MovieDao,
    private val apiKey: String
) : MovieRepository {

    // For Home screen: return a few items (previews). Try DB first, fallback
    //to network page=1.
    override suspend fun getNowPlayingPreview(limit: Int): List<Movie> {
        val cached = dao.getLimited("now_playing", limit)
        if (cached.isNotEmpty()) return cached.map { it.toDomain() }
        val response = api.getNowPlaying(apiKey, 1)
        val movies = response.results?.map { it.toDomain() }
// cache
        dao.insertAll(response.results!!.map { it.toEntity("now_playing", 1) })
        return if (movies.isNullOrEmpty()) emptyList()
        else movies.take(limit)
    }

    override suspend fun getTrendingPreview(limit: Int): List<Movie> {
        val cached = dao.getLimited("trending", limit)
        if (cached.isNotEmpty()) return cached.map { it.toDomain() }
        val response = api.getTrending(apiKey, 1)
        val movies = response.results?.map { it.toDomain() }
        dao.insertAll(response.results!!.map { it.toEntity("trending", 1) })
        return if (movies.isNullOrEmpty()) emptyList()
        else movies.take(limit)
    }

    private fun MovieEntity.toDomain() = Movie(
        id = id, title = title, overview
        = overview, posterPath = posterPath, backdropPath = backdropPath,
        releaseDate = releaseDate
    )

    private fun ResultsItemNowPlaying.toDomain() = Movie(
        id = id!!, title = title!!, overview
        = overview!!, posterPath = posterPath, backdropPath = backdropPath,
        releaseDate = releaseDate
    )

    private fun ResultsItemTrending.toDomain() = Movie(
        id = id!!, title = title!!, overview
        = overview!!, posterPath = posterPath, backdropPath = backdropPath,
        releaseDate = releaseDate
    )

    private fun ResultsItemNowPlaying.toEntity(category: String, page: Int) = MovieEntity(
        id = id!!, title = title!!, overview =
        overview!!, posterPath = posterPath, backdropPath = backdropPath,
        releaseDate = releaseDate, category = category, page = page
    )

    private fun ResultsItemTrending.toEntity(category: String, page: Int) = MovieEntity(
        id = id!!, title = title!!, overview =
        overview!!, posterPath = posterPath, backdropPath = backdropPath,
        releaseDate = releaseDate, category = category, page = page
    )

}