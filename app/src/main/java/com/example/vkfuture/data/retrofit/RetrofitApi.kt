package com.example.vkfuture.data.retrofit

import com.example.vkfuture.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApi {

    @GET("users.get")
//    @Headers("access_token: ${Delete.api}")
    suspend fun getUserById(@Query("access_token") token: String,
                            @Query("user_ids") id: String,
                            @Query("v") v: String
                            ): Response<User>
}