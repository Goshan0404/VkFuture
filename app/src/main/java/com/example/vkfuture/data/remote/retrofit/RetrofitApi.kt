package com.example.vkfuture.data.remote.retrofit

import com.example.vkfuture.data.remote.model.modelnews.PostsResponse
import com.example.vkfuture.utils.Token
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApi {

    @GET("newsfeed.get")
    suspend fun getNews(
        @Query("access_token") token: String = Token.accessToken,
        @Query("filters") filters: String,
        @Query("start_from") startFrom: String? = null,
        @Query("count") count: Int = 10,
        @Query("v") v: String = "5.131"
    ): Response<PostsResponse>

    @GET("account.getProfileInfo")
    suspend fun getProfileDetails(
        @Query("access_token") token: String = Token.accessToken,
        @Query("v") v: String = "5.131"
    ): Response<com.example.vkfuture.data.remote.model.modelprofiledetails.ProfileDetails>

    @GET("likes.add")
    suspend fun addLike(
        @Query("access_token") token: String = Token.accessToken,
        @Query("type") type: String,
        @Query("item_id") itemId: String,
        @Query("owner_id") ownerId: String,
        @Query("v") v: String = "5.131"
    )

    @GET("likes.delete")
    suspend fun deleteLike(
        @Query("access_token") token: String = Token.accessToken,
        @Query("type") type: String,
        @Query("item_id") itemId: String,
        @Query("owner_id") ownerId: String,
        @Query("v") v: String = "5.131"
    )

    @GET("wall.repost")
    suspend fun doRepost(
        @Query("object") objectId: String,
        @Query("message") message: String? = null
    )

    

}