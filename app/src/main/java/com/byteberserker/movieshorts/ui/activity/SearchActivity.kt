package com.byteberserker.movieshorts.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.byteberserker.movieshorts.databinding.ActivitySearchBinding
import com.byteberserker.movieshorts.di.DaggerAppComponent
import com.byteberserker.movieshorts.domain.model.Movie
import com.byteberserker.movieshorts.ui.adapter.SearchAdapter
import com.byteberserker.movieshorts.ui.viewmodel.MainViewModelFactory
import com.byteberserker.movieshorts.ui.viewmodel.SearchViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import com.byteberserker.movieshorts.R
import javax.inject.Inject

class SearchActivity : BaseActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var adapter: SearchAdapter

    @Inject
    lateinit var vmFactory: MainViewModelFactory

    private lateinit var viewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerAppComponent.factory().create(this).inject(this)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(this, vmFactory)[SearchViewModel::class.java]

        setupRecyclerView()
        setupSearchInput()
        observeSearchResults()
    }

    private fun setupRecyclerView() {
        adapter = SearchAdapter(
            onMovieClick = { movie ->
                openMovieDetail(movie)
            }
        )
        binding.rvSearchResults.layoutManager = LinearLayoutManager(this)
        binding.rvSearchResults.adapter = adapter
    }

    private fun setupSearchInput() {
        binding.etSearchInput.addTextChangedListener { text ->
            viewModel.updateSearchQuery(text.toString())
        }
        binding.etSearchInput.requestFocus()
        val imm = getSystemService(INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager
        imm.showSoftInput(binding.etSearchInput, android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT)
    }

    private fun observeSearchResults() {
        lifecycleScope.launch {
            viewModel.searchResults.collectLatest { results ->
                if (results.isEmpty()) {
                    binding.rvSearchResults.visibility = View.GONE
                    binding.tvEmptyState.visibility = View.VISIBLE
                    binding.tvEmptyState.text = if (binding.etSearchInput.text.toString().length >= 2) {
                        getString(R.string.no_movies_found)
                    } else {
                        getString(R.string.empty_state_search)
                    }
                } else {
                    binding.rvSearchResults.visibility = View.VISIBLE
                    binding.tvEmptyState.visibility = View.GONE
                    adapter.setMovies(results)
                }
            }
        }

        lifecycleScope.launch {
            viewModel.isLoading.collectLatest { isLoading ->
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
        }
    }

    private fun openMovieDetail(movie: Movie) {
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra("movie", movie)
        startActivity(intent)
    }


    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
