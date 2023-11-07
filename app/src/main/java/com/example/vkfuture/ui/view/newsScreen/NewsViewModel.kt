package com.example.vkfuture.ui.view.newsScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.vkfuture.data.local.entity.PostEntity
import com.example.vkfuture.data.repository.PostRepository
import com.example.vkfuture.data.repository.news.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    private val postRepository: PostRepository,
) : ViewModel() {

    private val _posts = MutableStateFlow<PagingData<PostEntity>>(PagingData.empty())
    val post = _posts


    private val dispatcherIo = Dispatchers.IO

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d("NewsViewModel", "Error: ${throwable.message}")
    }

    init {
        requestNews()
    }

    fun requestNews() {
        viewModelScope.launch(dispatcherIo + exceptionHandler) {
            newsRepository.getNews().cachedIn(viewModelScope).collect {
                _posts.value = it
            }
        }
    }

    fun userLikeChanged(status: Int, type: String, itemId: String, ownerId: String) {

        if (status == 1) {
            viewModelScope.launch(dispatcherIo + exceptionHandler) {
                postRepository.addLike(type, itemId, ownerId)
            }
        } else {
            viewModelScope.launch(dispatcherIo + exceptionHandler) {
                postRepository.deleteLike(type, itemId, ownerId)
            }
        }
    }
}