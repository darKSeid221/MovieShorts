package com.inshorts.movieshorts.ui

import android.content.res.Resources
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.inshorts.movieshorts.R
import com.inshorts.movieshorts.databinding.ActivityMainBinding
import com.inshorts.movieshorts.di.DaggerAppComponent
import com.inshorts.movieshorts.ui.viewmodel.MainViewModelFactory
import com.inshorts.movieshorts.ui.viewmodel.MovieViewmodel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var vmFactory: MainViewModelFactory

    private lateinit var viewmodel: MovieViewmodel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        DaggerAppComponent.factory().create(this).inject(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val topInset = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
            v.setPadding(v.paddingLeft, topInset + 8, v.paddingRight, v.paddingBottom)
            insets
        }
        viewmodel = ViewModelProvider(this, vmFactory)[MovieViewmodel::class.java]
        setupViews()

        val hero = binding.imgHero
        val search = binding.searchBar

        binding.scrollView.setOnScrollChangeListener { _, _, scrollY, _, _ ->

            // ⭐ Parallax effect
            hero.translationY = scrollY * 0.5f

            // ⭐ Fade-out hero
            val fade = 1f - (scrollY / 300f)
            hero.alpha = fade.coerceIn(0f, 1f)

            // ⭐ Shrink search bar
            val minH = 50f.dp
            val maxH = 60f.dp
            val newH = (maxH - (scrollY / 12f)).coerceIn(minH, maxH)
            search.layoutParams.height = newH.toInt()
            search.requestLayout()
            search.alpha = (0.85f + (scrollY / 800f)).coerceAtMost(1f)
        }



        observe()
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

        Glide.with(binding.imgHero.context)
            .load("https://image.tmdb.org/t/p/w500/g96wHxU7EnoIFwemb2RgohIXrgW.jpg")
            .into(binding.imgHero)
    }

    private fun startListActivity(category: String) {
        // val i = Intent(this, MovieListActivity::class.java)
        //i.putExtra("category", category)
        //startActivity(i)
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