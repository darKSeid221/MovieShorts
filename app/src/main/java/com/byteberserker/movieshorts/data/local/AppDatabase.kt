package com.byteberserker.movieshorts.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.byteberserker.movieshorts.data.local.dao.BookmarkDao
import com.byteberserker.movieshorts.data.local.dao.MovieDao
import com.byteberserker.movieshorts.data.local.entity.BookmarkEntity
import com.byteberserker.movieshorts.data.local.entity.MovieEntity

@Database(entities = [MovieEntity::class, BookmarkEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun bookmarkDao(): BookmarkDao
}