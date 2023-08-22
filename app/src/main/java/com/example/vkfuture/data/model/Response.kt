package com.example.vkfuture.data.model

data class Response(
    val groups: List<Group>,
    val items: List<Item>,
    val next_from: String,
    val profiles: List<Profile>
)