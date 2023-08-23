package com.example.vkfuture.data.repository


import com.example.vkfuture.data.model.modelnews.Token
import com.example.vkfuture.data.model.modelprofiledetails.ProfileDetails
import com.example.vkfuture.data.retrofit.RetrofitClient
import retrofit2.Response

class ProfileRepository {
    val retrofitApi = RetrofitClient.retrofitApi
    suspend fun getProfileDetails() : Response<ProfileDetails> {
        return retrofitApi.getProfileDetails(Token.accessToken)
    }
}