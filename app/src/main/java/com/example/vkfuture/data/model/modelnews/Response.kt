package com.example.vkfuture.data.model.modelnews

data class Response(
    val groups: ArrayList<Group>,
    val items: ArrayList<Item>,
    val next_from: String,
    val profiles: ArrayList<Profile>
)