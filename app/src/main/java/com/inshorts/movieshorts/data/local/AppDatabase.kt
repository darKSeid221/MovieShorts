package com.inshorts.movieshorts.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.inshorts.movieshorts.data.local.dao.MovieDao
import com.inshorts.movieshorts.data.local.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}