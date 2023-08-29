package com.example.vkfuture.data.repository

import com.example.vkfuture.utils.Token
import com.example.vkfuture.data.model.modelnews.Posts

import com.example.vkfuture.data.retrofit.RetrofitClient
import retrofit2.Response

class NewsRepository {
    private val retrofitApi = RetrofitClient.retrofitApi
    suspend fun getNews(filters: String): Response<Posts> {
        return retrofitApi.getNews(Token.accessToken, filters)
    }
}