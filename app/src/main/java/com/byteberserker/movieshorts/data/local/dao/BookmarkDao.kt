package com.byteberserker.movieshorts.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.byteberserker.movieshorts.data.local.entity.BookmarkEntity

@Dao
interface BookmarkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: BookmarkEntity)

    @Query("DELETE FROM bookmarks WHERE movieId = :movieId")
    suspend fun deleteBookmark(movieId: Int)

    @Query("SELECT * FROM bookmarks ORDER BY bookmarkedAt DESC")
    suspend fun getAllBookmarks(): List<BookmarkEntity>

    @Query("SELECT EXISTS(SELECT 1 FROM bookmarks WHERE movieId = :movieId)")
    suspend fun isBookmarked(movieId: Int): Boolean

    @Query("SELECT movieId FROM bookmarks")
    suspend fun getBookmarkIds(): List<Int>
}
