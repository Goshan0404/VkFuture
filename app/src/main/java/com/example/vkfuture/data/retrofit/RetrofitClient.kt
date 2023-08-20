package com.example.vkfuture.data.retrofit

import com.example.vkfuture.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    val retrofitApi: RetrofitApi = Retrofit
        .Builder()
        .baseUrl(BuildConfig.baseUrl)
        .addConverterFactory(
            GsonConverterFactory.create()
        )
        .build()
        .create(RetrofitApi::class.java)

}