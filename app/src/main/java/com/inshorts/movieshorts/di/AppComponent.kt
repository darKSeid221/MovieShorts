package com.inshorts.movieshorts.di

import com.inshorts.movieshorts.ui.HomeActivity
import dagger.Component

@Component(modules = [MovieModule::class])
interface AppComponent {
    fun inject(activity: HomeActivity)
}
