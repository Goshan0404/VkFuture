package com.example.vkfuture.data.model.modelnews

data class Doc(
    val access_key: String,
    val date: Int,
    val ext: String,
    val id: Int,
    val is_unsafe: Int,
    val owner_id: Int,
    val size: Int,
    val title: String,
    val type: Int,
    val url: String
)