package com.example.vkfuture.data.model.modelnews

data class Link(
    val caption: String,
    val description: String,
    val is_favorite: Boolean,
    val photo: PhotoX,
    val target: String,
    val title: String,
    val url: String
)