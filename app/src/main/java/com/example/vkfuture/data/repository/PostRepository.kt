package com.example.vkfuture.data.repository

import com.example.vkfuture.data.remote.retrofit.RetrofitApi
import javax.inject.Inject

class PostRepository @Inject constructor(private val retrofitApi: RetrofitApi) {
    suspend fun addLike(type: String, itemId: String, ownerId: String) {
        retrofitApi.addLike( type = type, itemId =  itemId, ownerId =  ownerId)
    }

    suspend fun deleteLike(type: String, itemId: String, ownerId: String) {
        retrofitApi.deleteLike(type = type, itemId =  itemId, ownerId =  ownerId)
    }
}