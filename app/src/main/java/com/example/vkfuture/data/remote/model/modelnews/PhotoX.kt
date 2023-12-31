package com.example.vkfuture.data.remote.model.modelnews

data class PhotoX(
    val access_key: String,
    val album_id: Int,
    val date: Int,
    val has_tags: Boolean,
    val id: Int,
    val owner_id: Int,
    val post_id: Int,
    val sizes: List<Size>,
    val tags: Tags,
    val text: String,
    val user_id: Int,
    val web_view_token: String
)