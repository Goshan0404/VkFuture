package com.example.vkfuture.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.vkfuture.data.remote.model.modelnews.Attachment
import com.example.vkfuture.data.remote.model.modelnews.Comments
import com.example.vkfuture.data.remote.model.modelnews.Item
import com.example.vkfuture.data.remote.model.modelnews.Likes
import com.example.vkfuture.data.remote.model.modelnews.Reposts
import com.example.vkfuture.data.remote.model.modelnews.Views

@Entity(tableName = "postEntity")
data class PostEntity(
    @PrimaryKey
    val id: Int,
    val type: String,
    val ownerName: String?,
    val text: String,
    val attachments: List<Attachment>,
    val comments: Comments,
    val date: Int,
    val likes: Likes,
    val owner_id: Int,
    val post_id: Int,
    val reposts: Reposts,
    val views: Views
) {
//    fun toPost(): Item {
//        return Item()
//    }
}