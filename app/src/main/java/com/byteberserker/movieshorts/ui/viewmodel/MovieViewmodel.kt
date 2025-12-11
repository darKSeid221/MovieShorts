package com.byteberserker.movieshorts.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.byteberserker.movieshorts.domain.usecase.NowPlayingMovieUseCase
import com.byteberserker.movieshorts.domain.usecase.TrendingMovieUseCase
import com.byteberserker.movieshorts.domain.model.Movie
import kotlinx.coroutines.Dispatchers
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
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val trending = trendingMovieUseCase.invoke()
                _trendingPreview.value = trending
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val nowPlaying = nowPlayingMovieUseCase.invoke()
                _nowPreview.value = nowPlaying
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}