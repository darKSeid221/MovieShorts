package com.byteberserker.movieshorts.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.byteberserker.movieshorts.databinding.ActivityProfileBinding
import com.byteberserker.movieshorts.di.DaggerAppComponent
import com.byteberserker.movieshorts.domain.usecase.BookmarkUseCase
import com.byteberserker.movieshorts.ui.adapter.BookmarkAdapter
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileActivity : BaseActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var adapter: BookmarkAdapter

    @Inject
    lateinit var bookmarkUseCase: BookmarkUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerAppComponent.factory().create(this).inject(this)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupRecyclerView()
        loadBookmarks()
    }

    private fun setupRecyclerView() {
        adapter = BookmarkAdapter { movie ->
            val intent = Intent(this, MovieDetailActivity::class.java)
            intent.putExtra("movie", movie)
            startActivity(intent)
        }
        binding.rvBookmarks.layoutManager = GridLayoutManager(this, 2)
        binding.rvBookmarks.adapter = adapter
    }

    private fun loadBookmarks() {
        lifecycleScope.launch {
            val bookmarks = bookmarkUseCase.getAllBookmarks()
            if (bookmarks.isEmpty()) {
                binding.tvEmptyState.visibility = View.VISIBLE
                binding.rvBookmarks.visibility = View.GONE
            } else {
                binding.tvEmptyState.visibility = View.GONE
                binding.rvBookmarks.visibility = View.VISIBLE
                adapter.setMovies(bookmarks)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Reload bookmarks when returning to this activity
        loadBookmarks()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
