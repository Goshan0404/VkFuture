package com.example.vkfuture.data.model.modelnews

data class Photo(
    val album_id: Int,
    val date: Int,
    val has_tags: Boolean,
    val id: Int,
    val owner_id: Int,
    val sizes: List<Size>,
    val text: String,
    val user_id: Int,
    val web_view_token: String
)