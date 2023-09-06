package com.example.vkfuture.data.repository

import com.example.vkfuture.data.retrofit.RetrofitApi
import com.example.vkfuture.data.retrofit.RetrofitClient
import com.example.vkfuture.utils.Token

class PostRepository {
    val retrofitApi: RetrofitApi = RetrofitClient.retrofitApi

    suspend fun addLike(type: String, itemId: String, ownerId: String) {
        retrofitApi.addLike(Token.accessToken, type, itemId, ownerId)
    }

    suspend fun deleteLike(type: String, itemId: String, ownerId: String) {
        retrofitApi.deleteLike(Token.accessToken, type, itemId, ownerId)
    }
}