package com.byteberserker.movieshorts.di

import android.content.Context
import com.byteberserker.movieshorts.ui.activity.HomeActivity
import com.byteberserker.movieshorts.ui.activity.MovieDetailActivity
import com.byteberserker.movieshorts.ui.activity.MovieListActivity
import com.byteberserker.movieshorts.ui.activity.ProfileActivity
import com.byteberserker.movieshorts.ui.activity.SearchActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, DatabaseModule::class, RepoModule::class])
interface AppComponent {
    fun inject(activity: HomeActivity)
    fun inject(activity: ProfileActivity)
    fun inject(activity: MovieListActivity)
    fun inject(activity: MovieDetailActivity)
    fun inject(activity: SearchActivity)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}
