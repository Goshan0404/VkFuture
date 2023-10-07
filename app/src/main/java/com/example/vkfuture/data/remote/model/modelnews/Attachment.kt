package com.example.vkfuture.data.remote.model.modelnews

data class Attachment(
    val doc: com.example.vkfuture.data.remote.model.modelnews.Doc,
    val link: com.example.vkfuture.data.remote.model.modelnews.Link,
    val photo: com.example.vkfuture.data.remote.model.modelnews.PhotoX,
    val type: String,
    val video: com.example.vkfuture.data.remote.model.modelnews.Video
)