package com.example.vkfuture.data.remote.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.vkfuture.NewsPagingSource
import com.example.vkfuture.data.local.entity.PostEntity
import com.example.vkfuture.data.remote.model.modelnews.Item
import com.example.vkfuture.data.remote.model.modelnews.PostsResponse
import com.example.vkfuture.data.remote.retrofit.RetrofitApi
import kotlinx.coroutines.flow.Flow

import retrofit2.Response
import javax.inject.Inject

class NewsRepository @Inject constructor (private val retrofitApi: RetrofitApi) {

     fun getNews(): Flow<PagingData<PostEntity>> {
        return Pager(config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { NewsPagingSource(retrofitApi) }).flow
    }
}