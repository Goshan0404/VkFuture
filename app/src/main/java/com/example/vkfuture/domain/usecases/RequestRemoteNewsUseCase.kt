package com.example.vkfuture.domain.usecases

import com.example.vkfuture.data.model.modelnews.Posts
import com.example.vkfuture.data.repository.NewsRepository
import retrofit2.Response

class RequestRemoteNewsUseCase {
    private val newsRepository = NewsRepository()

    suspend fun requestNews(): Response<Posts> {

        return newsRepository.getNews("post")
    }
}