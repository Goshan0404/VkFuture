package com.example.vkfuture.data.repository

import com.example.vkfuture.data.model.modelnews.News
import com.example.vkfuture.data.model.modelnews.Token
import com.example.vkfuture.data.retrofit.RetrofitClient
import retrofit2.Response

class NewsRepository {
    val retrofitApi = RetrofitClient.retrofitApi
    suspend fun getNews(filters: String): Response<News> {
        return retrofitApi.getNews(Token.accessToken, filters)
    }
}