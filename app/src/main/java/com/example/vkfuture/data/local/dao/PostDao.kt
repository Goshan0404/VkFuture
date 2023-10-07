package com.example.vkfuture.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.vkfuture.data.local.entity.PostEntity

@Dao
interface PostDao {

    @Query("SELECT * FROM postEntity")
    fun pagingSource(): PagingSource<Int, PostEntity>
    @Upsert
    suspend fun loadPosts(posts: List<PostEntity>?)
    @Query("DELETE FROM postEntity")
    suspend fun clearAllPosts()
}