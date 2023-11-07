package com.example.vkfuture.data.repository.news

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.vkfuture.data.local.entity.PostEntity
import com.example.vkfuture.data.remote.model.modelnews.Group
import com.example.vkfuture.data.remote.model.modelnews.PostsResponse
import com.example.vkfuture.data.remote.model.modelnews.Profile
import com.example.vkfuture.data.remote.retrofit.RetrofitApi
import retrofit2.HttpException
import retrofit2.Response

class NewsPagingSource(private val retrofitApi: RetrofitApi): PagingSource<String, PostEntity>() {


    override suspend fun load(params: LoadParams<String>): LoadResult<String, PostEntity> {

        val nextFrom = params.key
        val pageSize = params.loadSize
        val result = retrofitApi.getNews(filters = "post", startFrom = nextFrom, count = pageSize)

        if (result.isSuccessful) {
            val nextKey =checkNotNull(result.body()).response.next_from

            val posts = mapPosts(result)

            return LoadResult.Page(posts, null, nextKey)
        } else {
            return LoadResult.Error(HttpException(result))
        }
    }

    private fun mapPosts(
        result: Response<PostsResponse>,
    ): List<PostEntity> {
        val postsResult = checkNotNull(result.body()).response.items
        val groups = HashMap<Int, Group>()
        val profiles = HashMap<Int, Profile>()

        checkNotNull(result.body()).response.groups.forEach {
            groups[-it.id] = it
        }
        checkNotNull(result.body()).response.profiles.forEach {
            profiles[it.id] = it
        }
        val posts = postsResult.map {
            if (it.owner_id > 0)
                it.toEntity(checkNotNull(profiles[it.owner_id]))
            else
                it.toEntity(checkNotNull( groups[it.owner_id]))
        }
        return posts
    }

    override fun getRefreshKey(state: PagingState<String, PostEntity>): String? {
        val anchorPos = state.anchorPosition ?: return null
        val closePos = state.closestPageToPosition(anchorPos) ?: return null
        return closePos.nextKey
    }
}