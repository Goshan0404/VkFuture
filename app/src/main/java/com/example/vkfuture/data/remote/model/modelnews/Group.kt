package com.example.vkfuture.data.remote.model.modelnews

data class Group(
    val id: Int,
    val is_closed: Int,
    val name: String,
    val photo_100: String,
    val photo_200: String,
    val photo_50: String,
    val screen_name: String,
    val type: String
)