package com.inshorts.movieshorts.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.inshorts.movieshorts.data.local.entity.MovieEntity

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<MovieEntity>)

  //  @Query("SELECT * FROM movies WHERE category = :category ORDER BY id")
    //fun pagingSource(category: String): PagingSource<Int, MovieEntity>


    @Query("DELETE FROM movies WHERE category = :category")
    suspend fun clearCategory(category: String)

    @Query("SELECT * FROM movies WHERE category = :category ORDER BY page, id LIMIT :limit")
    suspend fun getLimited(category: String, limit: Int): List<MovieEntity>
}