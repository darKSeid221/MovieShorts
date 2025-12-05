package com.inshorts.movieshorts.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inshorts.movieshorts.domain.NowPlayingMovieUseCase
import com.inshorts.movieshorts.domain.TrendingMovieUseCase
import com.inshorts.movieshorts.domain.model.Movie
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieViewmodel @Inject constructor(
    private val nowPlayingMovieUseCase: NowPlayingMovieUseCase,
    private val trendingMovieUseCase: TrendingMovieUseCase
) : ViewModel() {

    private  var nowPlayingList =  MutableLiveData<List<Movie>> ()
    val liveDataNowPlayingList:LiveData<List<Movie>> = nowPlayingList

    fun getNowPlayingMovie(offset:Int, page:Int){
        viewModelScope.launch {
            val list = nowPlayingMovieUseCase.invoke()
            if(list.isNotEmpty()){
                nowPlayingList.postValue(list)
            }
        }
    }
}