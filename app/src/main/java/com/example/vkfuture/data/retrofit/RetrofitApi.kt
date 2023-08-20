package com.example.vkfuture.data.retrofit

import com.example.vkfuture.BuildConfig
import com.example.vkfuture.data.model.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface RetrofitApi {

    @GET("User")
    @Headers("X-API-KEY: ")
    suspend fun getUserById(@Query("id") id: Int): Response<User>

}