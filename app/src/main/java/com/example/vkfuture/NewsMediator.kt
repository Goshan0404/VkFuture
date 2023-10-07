package com.example.vkfuture

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.vkfuture.data.local.DataBase
import com.example.vkfuture.data.local.entity.PostEntity
import com.example.vkfuture.data.local.entity.RemoteKey
import com.example.vkfuture.data.remote.model.modelnews.Group
import com.example.vkfuture.data.remote.model.modelnews.Response
import com.example.vkfuture.data.remote.model.modelnews.Profile
import com.example.vkfuture.data.remote.repository.NewsRepository
import com.example.vkfuture.data.remote.retrofit.RetrofitApi
import retrofit2.HttpException
import java.io.IOException
@OptIn(ExperimentalPagingApi::class)
class NewsMediator(
    private val database: DataBase,
    private val retrofitApi: RetrofitApi,
) : RemoteMediator<Int, PostEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PostEntity>,
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND ->
                    return MediatorResult.Success(
                        endOfPaginationReached = true
                    )
                LoadType.APPEND -> {
                    val remoteKey = database.withTransaction {
                        database.keyDao.getRemoteKey()
                    }
                    remoteKey.nextKey
                }
            }

            val response =
                retrofitApi.getNews("post", loadKey.toString())
            val result = response.body()?.response
            val posts = result?.items
            val groupsName = hashMapOf<Int, Group>()
            val profilesName = hashMapOf<Int, Profile>()
            setOwnerNames(result, groupsName, profilesName)
            val remoteKey = result?.next_from

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.postDao.clearAllPosts()
                    database.keyDao.deleteKey()
                }

                val postEntity = posts?.map {
                    if (it.owner_id < 0) {
                        it.toEntity(groupsName[it.owner_id])
                    } else {
                        it.toEntity(profilesName[it.owner_id])
                    }
                }

                database.postDao.loadPosts(postEntity)
                database.keyDao.insertOrReplace(RemoteKey(remoteKey))
            }

            MediatorResult.Success(
                endOfPaginationReached = posts?.isEmpty() == true
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    private fun setOwnerNames(
        result: Response?,
        groups: HashMap<Int, Group>,
        profiles: HashMap<Int, Profile>,
    ) {

        result?.groups?.forEach {
            groups[it.id] = it
        }

        result?.profiles?.forEach {
            profiles[it.id] = it
        }
    }
}