package com.example.vkfuture.data.remote.model.modelnews

import com.example.vkfuture.data.local.entity.PostEntity

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
) {
    fun toEntity(group: Group): PostEntity {
            return PostEntity(
                type = type, ownerName = group.name, ownerPhoto = group.photo_100,
                attachments = attachments, text = text, comments = comments, date = date, likes = likes,
                ownerId = owner_id, reposts = reposts, views = views, postId = post_id)

    }

    fun toEntity(profile: Profile): PostEntity {
            return PostEntity(
                type = type, ownerName = "${profile.first_name} ${profile.last_name}",
                ownerPhoto = profile.photo_100,
                attachments = attachments, text = text, comments = comments, date = date, likes = likes,
                ownerId = owner_id, reposts = reposts, views = views, postId = post_id)
    }
}