package com.inshorts.movieshorts.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.inshorts.movieshorts.R
import com.inshorts.movieshorts.databinding.ActivityMovieDetailBinding
import com.inshorts.movieshorts.domain.model.Movie

class MovieDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailBinding
    private var isBookmarked = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val movie = intent.getParcelableExtra<Movie>("movie")
        if (movie != null) setupUI(movie)
    }

    private fun setupUI(movie: Movie) {
        val posterUrl = "https://image.tmdb.org/t/p/w500${movie.posterPath}"

        Glide.with(binding.ivPoster.context)
            .load(posterUrl)
            .centerCrop()           // optional
            .thumbnail(0.1f)        // optional
            .into(binding.ivPoster)

        binding.tvTitle.text = movie.title
        binding.tvOverview.text = movie.overview
        binding.tvRelease.text = "Release: ${movie.releaseDate ?: "N/A"}"

        binding.btnBookmark.setOnClickListener {
            isBookmarked = !isBookmarked
            binding.btnBookmark.setImageResource(
                if (isBookmarked) R.drawable.ic_bookmark_filled
                else R.drawable.ic_bookmark
            )
        }
    }

}