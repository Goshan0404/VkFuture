package com.example.vkfuture.data.model.modelnews

data class Item(
    val attachments: List<Attachment>,
    val can_doubt_category: Boolean,
    val can_set_category: Boolean,
    val carousel_offset: Int,
    val comments: Comments,
    val copyright: Copyright,
    val date: Int,
    val donut: Donut,
    val edited: Int,
    val id: Int,
    val is_favorite: Boolean,
    val likes: Likes,
    val marked_as_ads: Int,
    val owner_id: Int,
    val post_id: Int,
    val post_source: PostSource,
    val post_type: String,
    val reposts: Reposts,
    val short_text_rate: Double,
    val signer_id: Int,
    val source_id: Int,
    val text: String,
    val topic_id: Int,
    val type: String,
    val views: Views
)