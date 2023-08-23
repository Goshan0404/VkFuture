package com.example.vkfuture.ui.stateholders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vkfuture.data.model.modelnews.News
import com.example.vkfuture.data.repository.NewsRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel: ViewModel() {
    val newsRepository = NewsRepository()
    fun userAuthorizated(callBack: (news: News?, arrayOfHasMaps: Array<HashMap<Int, Any>>) -> Unit) {

        viewModelScope.launch {
            val response: Deferred<Response<News>> = async {
                newsRepository.getNews("Post")
            }
            response.await()
            val result =  response.getCompleted().body()!!.response

            val hasMapGroups = HashMap<Int, Any>()
            val hasMapProfiles = HashMap<Int, Any>()

            result.groups.forEach {
                hasMapGroups[it.id] = it
            }

            result.profiles.forEach {
                hasMapProfiles[it.id] = it
            }

            val arrayOfHasMaps = arrayOf(hasMapGroups, hasMapProfiles)

            callBack.invoke(response.getCompleted().body(), arrayOfHasMaps)
        }
    }
}