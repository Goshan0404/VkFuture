package com.example.vkfuture.data.model.modelnews

data class Link(
    val button_action: String,
    val button_text: String,
    val description: String,
    val is_favorite: Boolean,
    val photo: Photo,
    val target: String,
    val title: String,
    val url: String
)