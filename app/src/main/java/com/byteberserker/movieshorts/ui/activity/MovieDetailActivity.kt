package com.byteberserker.movieshorts.ui.activity

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.byteberserker.movieshorts.R
import com.byteberserker.movieshorts.databinding.ActivityMovieDetailBinding
import com.byteberserker.movieshorts.di.DaggerAppComponent
import com.byteberserker.movieshorts.domain.usecase.BookmarkUseCase
import com.byteberserker.movieshorts.domain.model.Movie
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieDetailActivity : BaseActivity() {
    private lateinit var binding: ActivityMovieDetailBinding
    private var currentMovie: Movie? = null

    @Inject
    lateinit var bookmarkUseCase: BookmarkUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerAppComponent.factory().create(this).inject(this)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // Setup toolbar with back button
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
        
        // Handle both regular intent and deep link
        val movie = intent.getParcelableExtra<Movie>("movie")
        if (movie != null) {
            currentMovie = movie
            setupUI(movie)
            loadBookmarkStatus(movie.id)
        } else {
            // Handle deep link
            handleDeepLink()
        }
    }

    private fun handleDeepLink() {
        val data = intent.data
        if (data != null && data.scheme == "movieshorts" && data.host == "movie") {
            val movieId = data.pathSegments.firstOrNull()?.toIntOrNull()
            val movieTitle = data.getQueryParameter("title")
            
            if (movieId != null) {
                // Create a minimal movie object - in production, you'd fetch full details from API
                val movie = Movie(
                    id = movieId,
                    title = movieTitle ?: "Movie",
                    overview = "Loading...",
                    posterPath = null,
                    backdropPath = null,
                    releaseDate = null
                )
                currentMovie = movie
                setupUI(movie)
                loadBookmarkStatus(movieId)
            } else {
                finish()
            }
        } else {
            finish()
        }
    }

    private fun loadBookmarkStatus(movieId: Int) {
        lifecycleScope.launch {
            val isBookmarked = bookmarkUseCase.isMovieBookmarked(movieId)
            currentMovie?.isBookmarked = isBookmarked
            updateBookmarkIcon(isBookmarked)
        }
    }

    private fun setupUI(movie: Movie) {
        val posterUrl = "https://image.tmdb.org/t/p/w500${movie.posterPath}"

        Glide.with(binding.ivPoster.context)
            .load(posterUrl)
            .centerCrop()
            .thumbnail(0.1f)
            .into(binding.ivPoster)

        binding.tvTitle.text = movie.title
        binding.tvOverview.text = movie.overview
        binding.tvRelease.text = "Release: ${movie.releaseDate ?: "N/A"}"

        binding.btnBookmark.setOnClickListener {
            toggleBookmark()
        }
    }

    private fun toggleBookmark() {
        currentMovie?.let { movie ->
            lifecycleScope.launch {
                bookmarkUseCase.toggleBookmark(movie)
                val newStatus = bookmarkUseCase.isMovieBookmarked(movie.id)
                movie.isBookmarked = newStatus
                updateBookmarkIcon(newStatus)
            }
        }
    }

    private fun updateBookmarkIcon(isBookmarked: Boolean) {
        binding.btnBookmark.setImageResource(
            if (isBookmarked) R.drawable.ic_bookmark_filled
            else R.drawable.ic_bookmark
        )
    }
}