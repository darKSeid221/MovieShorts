package com.byteberserker.movieshorts.data.repository

import com.byteberserker.movieshorts.data.local.dao.BookmarkDao
import com.byteberserker.movieshorts.data.local.dao.MovieDao
import com.byteberserker.movieshorts.data.local.entity.BookmarkEntity
import com.byteberserker.movieshorts.data.local.entity.MovieEntity
import com.byteberserker.movieshorts.data.remote.api.TMDBAPIService
import com.byteberserker.movieshorts.data.remote.dto.ResultsItemNowPlaying
import com.byteberserker.movieshorts.data.remote.dto.ResultsItemTrending
import com.byteberserker.movieshorts.domain.model.Movie
import com.byteberserker.movieshorts.domain.repository.MovieRepository
import javax.inject.Inject
import javax.inject.Named

class MovieRepositoryImpl @Inject constructor(
    private val api: TMDBAPIService,
    private val dao: MovieDao,
    private val bookmarkDao: BookmarkDao,
    @Named("TMDB_API_KEY") private val apiKey: String
) : MovieRepository {

    // For Home screen: return a few items (previews). Try DB first, fallback
    //to network page=1.
    override suspend fun getNowPlayingPreview(limit: Int): List<Movie> {
        return try {
            val cached = dao.getLimited("now_playing", limit)
            if (cached.isNotEmpty()) return cached.map { it.toDomain() }
            
            val response = api.getNowPlaying(apiKey, 1)
            val movies = response.results?.map { it.toDomain() } ?: emptyList()
            
            // cache
            if (movies.isNotEmpty()) {
                dao.insertAll(response.results!!.map { it.toEntity("now_playing", 1) })
            }
            
            movies.take(limit)
        } catch (e: Exception) {
            e.printStackTrace()
            // Return cached data even if old, or empty list
            dao.getLimited("now_playing", limit).map { it.toDomain() }
        }
    }

    override suspend fun getTrendingPreview(limit: Int): List<Movie> {
        return try {
            val cached = dao.getLimited("trending", limit)
            if (cached.isNotEmpty()) return cached.map { it.toDomain() }
            
            val response = api.getTrending(apiKey, 1)
            val movies = response.results?.map { it.toDomain() } ?: emptyList()
            
            // cache
            if (movies.isNotEmpty()) {
                dao.insertAll(response.results!!.map { it.toEntity("trending", 1) })
            }
            
            movies.take(limit)
        } catch (e: Exception) {
            e.printStackTrace()
            // Return cached data even if old, or empty list
            dao.getLimited("trending", limit).map { it.toDomain() }
        }
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

    private fun com.byteberserker.movieshorts.data.remote.dto.ResultsItemSearch.toDomain() = Movie(
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

    // Pagination methods
    override suspend fun getTrendingMovies(page: Int): List<Movie> {
        return try {
            val response = api.getTrending(apiKey, page)
            response.results?.map { it.toDomain() } ?: emptyList()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun getNowPlayingMovies(page: Int): List<Movie> {
        return try {
            val response = api.getNowPlaying(apiKey, page)
            response.results?.map { it.toDomain() } ?: emptyList()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    // Search method
    override suspend fun searchMovies(query: String, page: Int): List<Movie> {
        return try {
            val response = api.searchMovies(apiKey, query, page)
            response.results?.map { it.toDomain() } ?: emptyList()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    // Bookmark methods
    override suspend fun addBookmark(movie: Movie) {
        val bookmark = BookmarkEntity(
            movieId = movie.id,
            title = movie.title,
            overview = movie.overview,
            posterPath = movie.posterPath,
            backdropPath = movie.backdropPath,
            releaseDate = movie.releaseDate
        )
        bookmarkDao.insertBookmark(bookmark)
    }

    override suspend fun removeBookmark(movieId: Int) {
        bookmarkDao.deleteBookmark(movieId)
    }

    override suspend fun getAllBookmarks(): List<Movie> {
        return bookmarkDao.getAllBookmarks().map { it.toDomainMovie() }
    }

    override suspend fun isMovieBookmarked(movieId: Int): Boolean {
        return bookmarkDao.isBookmarked(movieId)
    }

    private fun BookmarkEntity.toDomainMovie() = Movie(
        id = movieId,
        title = title,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        releaseDate = releaseDate,
        isBookmarked = true
    )

}