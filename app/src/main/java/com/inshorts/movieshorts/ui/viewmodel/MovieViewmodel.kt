package com.inshorts.movieshorts.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inshorts.movieshorts.domain.NowPlayingMovieUseCase
import com.inshorts.movieshorts.domain.TrendingMovieUseCase
import com.inshorts.movieshorts.domain.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieViewmodel @Inject constructor(
    private val nowPlayingMovieUseCase: NowPlayingMovieUseCase,
    private val trendingMovieUseCase: TrendingMovieUseCase
) : ViewModel() {

    private val _trendingPreview = MutableStateFlow<List<Movie>>(emptyList())
    val trendingPreview: StateFlow<List<Movie>> = _trendingPreview
    private val _nowPreview = MutableStateFlow<List<Movie>>(emptyList())
    val nowPreview: StateFlow<List<Movie>> = _nowPreview

    init {
        loadPreviews()
    }

    private fun loadPreviews() {
        viewModelScope.launch {
            _trendingPreview.value = trendingMovieUseCase.invoke()
            _nowPreview.value = nowPlayingMovieUseCase.invoke()
        }
    }

}