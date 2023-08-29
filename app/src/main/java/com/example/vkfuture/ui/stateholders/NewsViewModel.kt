package com.example.vkfuture.ui.stateholders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vkfuture.LoadState
import com.example.vkfuture.data.model.modelnews.Group
import com.example.vkfuture.data.model.modelnews.Item
import com.example.vkfuture.data.model.modelnews.Posts
import com.example.vkfuture.data.model.modelnews.Profile
import com.example.vkfuture.data.repository.LikesRepository
import com.example.vkfuture.data.repository.NewsRepository
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
    private val likesRepository = LikesRepository()

    private val dispatcherIo = Dispatchers.IO

    init {
        requestNews()
    }

    fun requestNews() {
        _loadState.value = LoadState(isLoading = true)

        viewModelScope.launch {
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

    fun userLikeChanged(status: Int, type: String, itemId: String) {

        if (status == 0) {
            viewModelScope.launch(dispatcherIo) {
                likesRepository.addLike(type, itemId)
            }
        } else {
            viewModelScope.launch {
                likesRepository.deleteLike(type, itemId)
            }
        }
    }
}

