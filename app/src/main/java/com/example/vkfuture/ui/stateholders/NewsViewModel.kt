package com.example.vkfuture.ui.stateholders

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vkfuture.NetworkConnectionObserver
import com.example.vkfuture.ui.model.LoadState
import com.example.vkfuture.data.model.modelnews.Group
import com.example.vkfuture.data.model.modelnews.Item
import com.example.vkfuture.data.model.modelnews.Posts
import com.example.vkfuture.data.model.modelnews.Profile
import com.example.vkfuture.data.repository.PostRepository
import com.example.vkfuture.data.repository.NewsRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel : ViewModel() {

    private val _loadState = MutableStateFlow(LoadState())
    val loadState: StateFlow<LoadState> = _loadState.asStateFlow()

    private val _posts = MutableStateFlow(listOf<Item>())
    val post = _posts.asStateFlow()

    private val _profiles = MutableStateFlow(HashMap<Int, Profile>())
    val profiles = _profiles.asStateFlow()

    private val _groups = MutableStateFlow(HashMap<Int, Group>())
    val groups = _groups.asStateFlow()

    private val newsRepository = NewsRepository()
    private val postRepository = PostRepository()

    private val dispatcherIo = Dispatchers.IO

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _loadState.value = LoadState(isError = true)
        Log.d("NewsViewModel", "Error: ${throwable.message}")
    }

    init {
        requestNews()
    }

    fun requestNews() {
        _loadState.value = LoadState(isLoading = true)

        viewModelScope.launch(dispatcherIo + exceptionHandler) {
            val response: Deferred<Response<Posts>> = async(dispatcherIo) {
                newsRepository.getNews("post")
            }
            response.await()

            val result = response.getCompleted().body()!!.response

            setProperties(result)
            _loadState.value = LoadState(isLoading = false)
        }
    }

    private fun setProperties(result: com.example.vkfuture.data.model.modelnews.Response) {
        _posts.value = result.items

        result.groups.forEach {
            _groups.value[it.id] = it
        }

        result.profiles.forEach {
            _profiles.value[it.id] = it
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