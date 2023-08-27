package com.example.vkfuture.ui.stateholders

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vkfuture.State
import com.example.vkfuture.data.model.modelnews.Group
import com.example.vkfuture.data.model.modelnews.Item
import com.example.vkfuture.data.model.modelnews.Posts
import com.example.vkfuture.data.model.modelnews.Profile
import com.example.vkfuture.data.repository.NewsRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel : ViewModel() {
    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state.asStateFlow()

    private val _posts = MutableStateFlow(mutableListOf<Item>())
    val post = _posts.asStateFlow()

    private val _profiles = MutableStateFlow(HashMap<Int, Group>())
    val profiles = _posts.asStateFlow()

    private val _groups = MutableStateFlow(HashMap<Int, Profile>())
    val groups = _posts.asStateFlow()

    val newsRepository = NewsRepository()
    fun requestNews() {

        viewModelScope.launch {
            val response: Deferred<Response<Posts>> = async {
                newsRepository.getNews("Post")
            }
            response.await()

            val result = response.getCompleted().body()!!.response
            _posts.value = result.items as SnapshotStateList<Item>


            val groups = HashMap<Int, Group>()
            val profiles = HashMap<Int, Profile>()

            result.groups.forEach {
                groups[it.id] = it
            }

            result.profiles.forEach {
                profiles[it.id] = it
            }

        }
    }


}