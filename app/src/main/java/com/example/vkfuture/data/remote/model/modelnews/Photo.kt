package com.example.vkfuture.data.remote.model.modelnews

data class Photo(
    val album_id: Int,
    val date: Int,
    val has_tags: Boolean,
    val id: Int,
    val owner_id: Int,
    val sizes: List<com.example.vkfuture.data.remote.model.modelnews.Size>,
    val text: String,
    val user_id: Int,
    val web_view_token: String
)