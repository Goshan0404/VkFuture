package com.example.vkfuture.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.vkfuture.data.local.dao.PostDao
import com.example.vkfuture.data.local.dao.RemoteKeyDao
import com.example.vkfuture.data.local.entity.PostEntity
import com.example.vkfuture.data.local.entity.RemoteKey

@Database(
    entities = [PostEntity::class, RemoteKey::class],
    version = 1
)
abstract class DataBase: RoomDatabase() {
    abstract val postDao: PostDao
    abstract val keyDao: RemoteKeyDao

}