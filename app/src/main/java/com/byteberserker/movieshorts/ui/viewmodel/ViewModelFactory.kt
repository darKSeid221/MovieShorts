package com.byteberserker.movieshorts.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.byteberserker.movieshorts.domain.usecase.NowPlayingMovieUseCase
import com.byteberserker.movieshorts.domain.usecase.SearchMoviesUseCase
import com.byteberserker.movieshorts.domain.usecase.TrendingMovieUseCase
import javax.inject.Inject

class MainViewModelFactory @Inject constructor(
    private val nowPlayingMovieUseCase: NowPlayingMovieUseCase,
    private val trendingMovieUseCase: TrendingMovieUseCase,
    private val searchMoviesUseCase: SearchMoviesUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieViewmodel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MovieViewmodel(nowPlayingMovieUseCase, trendingMovieUseCase) as T
        }
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchViewModel(searchMoviesUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}