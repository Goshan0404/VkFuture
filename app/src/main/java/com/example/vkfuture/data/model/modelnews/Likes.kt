package com.example.vkfuture.data.model.modelnews

data class Likes(
    val can_like: Int,
    val can_publish: Int,
    val count: Int,
    val repost_disabled: Boolean,
    val user_likes: Int
)