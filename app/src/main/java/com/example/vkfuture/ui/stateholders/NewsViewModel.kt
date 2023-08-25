package com.example.vkfuture.ui.stateholders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vkfuture.data.model.modelnews.Group
import com.example.vkfuture.data.model.modelnews.Item
import com.example.vkfuture.data.model.modelnews.News
import com.example.vkfuture.data.model.modelnews.Profile
import com.example.vkfuture.data.repository.NewsRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel: ViewModel() {
    val newsRepository = NewsRepository()
    fun userAuthorizated(
        callBack: (news: List<Item>,
                   hasMapGroups: HashMap<Int, Group>,
                   hasMapProfiles: HashMap<Int, Profile>) -> Unit) {

        viewModelScope.launch {
            val response: Deferred<Response<News>> = async {
                newsRepository.getNews("Post")
            }
            response.await()

            val result =  response.getCompleted().body()!!.response

            val groups = HashMap<Int, Group>()
            val profiles = HashMap<Int, Profile>()

            result.groups.forEach {
                groups[it.id] = it
            }

            result.profiles.forEach {
                profiles[it.id] = it
            }

            val arrayOfHasMaps = arrayOf(groups, profiles)

            callBack.invoke(response.getCompleted().body()!!.response.items, groups, profiles)
        }
    }
}