package com.example.vkfuture.data.local.entity

import androidx.room.Entity

@Entity(tableName = "remote_keys")
data class RemoteKey(
    val nextKey: String?
)