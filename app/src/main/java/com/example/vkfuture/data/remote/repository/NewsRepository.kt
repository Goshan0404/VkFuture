package com.example.vkfuture.data.remote.repository

import com.example.vkfuture.data.remote.model.modelnews.PostsResponse
import com.example.vkfuture.data.remote.retrofit.RetrofitApi

import retrofit2.Response
import javax.inject.Inject

class NewsRepository @Inject constructor (private val retrofitApi: RetrofitApi) {

    suspend fun getNews(filters: String, startFrom: String? = null): Response<PostsResponse> {
        return retrofitApi.getNews(filters = filters, startFrom = startFrom)
    }
}