package com.example.vkfuture.ui.stateholders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vkfuture.data.model.News
import com.example.vkfuture.data.model.Post
import com.example.vkfuture.data.repository.NewsRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel: ViewModel() {
    val newsRepository = NewsRepository()
    fun userAuthorizated(callBack: (news: News?) -> Unit) {
        viewModelScope.launch {
            val response: Deferred<Response<News>> = async {
                newsRepository.getNews("Post")
            }
            response.await()
            callBack.invoke(response.getCompleted().body())
        }
    }
}