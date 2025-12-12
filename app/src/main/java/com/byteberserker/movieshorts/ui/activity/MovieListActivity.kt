package com.byteberserker.movieshorts.ui.activity

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.byteberserker.movieshorts.databinding.ActivityMovieListBinding
import com.byteberserker.movieshorts.di.DaggerAppComponent
import com.byteberserker.movieshorts.domain.usecase.GetNowPlayingMoviesUseCase
import com.byteberserker.movieshorts.domain.usecase.GetTrendingMoviesUseCase
import com.byteberserker.movieshorts.ui.adapter.MovieListAdapter
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieListActivity : BaseActivity() {

    private lateinit var binding: ActivityMovieListBinding
    private lateinit var adapter: MovieListAdapter
    private var currentPage = 1
    private var isLoading = false
    private var category: String = "trending"

    @Inject
    lateinit var getTrendingMoviesUseCase: GetTrendingMoviesUseCase

    @Inject
    lateinit var getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerAppComponent.factory().create(this).inject(this)
        binding = ActivityMovieListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        category = intent.getStringExtra("category") ?: "trending"

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = if (category == "trending") "Trending Movies" else "Now Playing"

        setupRecyclerView()
        loadMovies()
    }

    private fun setupRecyclerView() {
        adapter = MovieListAdapter()
        val layoutManager = GridLayoutManager(this, 2)
        binding.rvMovies.layoutManager = layoutManager
        binding.rvMovies.adapter = adapter

        binding.rvMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!isLoading && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount - 4) {
                    loadMovies()
                }
            }
        })
    }

    private fun loadMovies() {
        if (isLoading) return
        
        isLoading = true
        binding.progressBar.visibility = View.VISIBLE

        lifecycleScope.launch {
            try {
                val movies = if (category == "trending") {
                    getTrendingMoviesUseCase(currentPage)
                } else {
                    getNowPlayingMoviesUseCase(currentPage)
                }
                
                adapter.addMovies(movies)
                currentPage++
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading = false
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
