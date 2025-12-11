package com.byteberserker.movieshorts.di

import android.content.Context
import androidx.room.Room
import com.byteberserker.movieshorts.data.local.AppDatabase
import com.byteberserker.movieshorts.data.local.dao.BookmarkDao
import com.byteberserker.movieshorts.data.local.dao.MovieDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context, AppDatabase::class.java,
            "movies_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideMovieDao(db: AppDatabase): MovieDao = db.movieDao()

    @Provides
    fun provideBookmarkDao(db: AppDatabase): BookmarkDao = db.bookmarkDao()
}