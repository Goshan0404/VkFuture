package com.example.vkfuture.data.remote.model.modelnews

data class Likes(
    val can_like: Int,
    val can_publish: Int,
    var count: Int,
    val repost_disabled: Boolean,
    var user_likes: Int
)