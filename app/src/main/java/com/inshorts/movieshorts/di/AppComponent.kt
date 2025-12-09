package com.inshorts.movieshorts.di

import android.content.Context
import com.inshorts.movieshorts.ui.HomeActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MovieModule::class])
interface AppComponent {
    fun inject(activity: HomeActivity)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}
