package com.example.vkfuture.data.local.entity

import com.example.vkfuture.data.remote.model.modelnews.Attachment
import com.example.vkfuture.data.remote.model.modelnews.Comments
import com.example.vkfuture.data.remote.model.modelnews.Likes
import com.example.vkfuture.data.remote.model.modelnews.Reposts
import com.example.vkfuture.data.remote.model.modelnews.Views


data class PostEntity(
    val type: String,
    val ownerName: String?,
    val ownerPhoto: String?,
    val text: String,
    val attachments: List<Attachment>,
    val comments: Comments,
    val date: Int,
    val likes: Likes,
    val ownerIdd: Int,
    val postId: Int,
    val reposts: Reposts,
    val views: Views
) {
//    fun toPost(): Item {
//        return Item()
//    }
}