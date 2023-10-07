package com.example.vkfuture.data.remote.model.modelnews

data class Link(
    val button_action: String,
    val button_text: String,
    val description: String,
    val is_favorite: Boolean,
    val photo: com.example.vkfuture.data.remote.model.modelnews.Photo,
    val target: String,
    val title: String,
    val url: String
)