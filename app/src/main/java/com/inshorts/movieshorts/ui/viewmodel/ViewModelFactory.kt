package com.inshorts.movieshorts.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.inshorts.movieshorts.domain.NowPlayingMovieUseCase
import com.inshorts.movieshorts.domain.TrendingMovieUseCase
import javax.inject.Inject

class MainViewModelFactory @Inject constructor(private val nowPlayingMovieUseCase: NowPlayingMovieUseCase ,
                                               private val trendingMovieUseCase: TrendingMovieUseCase
    ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieViewmodel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MovieViewmodel(nowPlayingMovieUseCase,trendingMovieUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}