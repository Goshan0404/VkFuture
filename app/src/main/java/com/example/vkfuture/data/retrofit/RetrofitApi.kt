package com.example.vkfuture.data.retrofit

import com.example.vkfuture.data.model.modelnews.News
import com.example.vkfuture.data.model.modelprofiledetails.ProfileDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApi {

    @GET("newsfeed.get")
    suspend fun getNews(@Query("access_token") token: String,
                        @Query("filters") filters: String,
                        @Query("v") v: String = "5.131"): Response<News>

    @GET("account.getProfileInfo")
    suspend fun getProfileDetails(@Query("access_token") token : String,
                                  @Query("v") v : String = "5.131") : Response<ProfileDetails>
}