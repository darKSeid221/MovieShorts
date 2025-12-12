package com.byteberserker.movieshorts.ui.activity

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import androidx.activity.enableEdgeToEdge

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.byteberserker.movieshorts.databinding.ActivityMainBinding
import com.byteberserker.movieshorts.di.DaggerAppComponent
import com.byteberserker.movieshorts.ui.adapter.NowPlayingPreviewAdapter
import com.byteberserker.movieshorts.ui.adapter.TrendingPreviewAdapter
import com.byteberserker.movieshorts.ui.viewmodel.MainViewModelFactory
import com.byteberserker.movieshorts.ui.viewmodel.MovieViewmodel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var vmFactory: MainViewModelFactory

    private lateinit var viewmodel: MovieViewmodel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = androidx.activity.SystemBarStyle.dark(android.graphics.Color.BLACK)
        )
        DaggerAppComponent.factory().create(this).inject(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewmodel = ViewModelProvider(this, vmFactory)[MovieViewmodel::class.java]
        setupViews()

        val hero = binding.imgHero
        val search = binding.searchBar

        binding.scrollView.setOnScrollChangeListener { _, _, scrollY, _, _ ->

            // ⭐ Parallax effect
            hero.translationY = scrollY * 0.5f
        }


        observe()
    }

    override fun onResume() {
        super.onResume()
        // Set home as selected when returning from other activities
        binding.bottomNav.selectedItemId = com.byteberserker.movieshorts.R.id.nav_home
    }

    val Float.dp get() = this * Resources.getSystem().displayMetrics.density

    private fun setupViews() {
        binding.rvTrendingHome.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL, false
        )
        binding.rvNowHome.layoutManager = GridLayoutManager(this, 2)
        binding.tvSeeAllTrending.setOnClickListener {
            startListActivity("trending")
        }
        binding.tvSeeAllNow.setOnClickListener {
            startListActivity("now_playing")
        }

        binding.etSearch.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }

        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                com.byteberserker.movieshorts.R.id.nav_home -> {
                    true
                }
                com.byteberserker.movieshorts.R.id.nav_search -> {
                    startActivity(Intent(this, SearchActivity::class.java))
                    true
                }
                com.byteberserker.movieshorts.R.id.nav_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                else -> false
            }
        }

        Glide.with(binding.imgHero.context)
            .load("https://image.tmdb.org/t/p/w500/g96wHxU7EnoIFwemb2RgohIXrgW.jpg")
            .into(binding.imgHero)
    }

    private fun startListActivity(category: String) {
        val intent = Intent(this, MovieListActivity::class.java)
        intent.putExtra("category", category)
        startActivity(intent)
    }

    private fun observe() {
        lifecycleScope.launch {
            viewmodel.trendingPreview.collectLatest { list ->
                binding.rvTrendingHome.adapter = TrendingPreviewAdapter(list) { /*open*/ }
            }
        }
        lifecycleScope.launch {
            viewmodel.nowPreview.collectLatest { list ->
                binding.rvNowHome.adapter = NowPlayingPreviewAdapter(list) { /*open*/ }
            }
        }
    }
}