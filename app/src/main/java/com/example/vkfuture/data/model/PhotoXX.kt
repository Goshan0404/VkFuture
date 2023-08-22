package com.example.vkfuture.data.model

data class PhotoXX(
    val access_key: String,
    val album_id: Int,
    val date: Int,
    val has_tags: Boolean,
    val id: Int,
    val owner_id: Int,
    val post_id: Int,
    val sizes: List<SizeX>,
    val tags: Tags,
    val text: String,
    val user_id: Int
)