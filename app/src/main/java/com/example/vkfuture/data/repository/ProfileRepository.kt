package com.example.vkfuture.data.repository


import com.example.vkfuture.data.remote.model.modelprofiledetails.ProfileDetails
import com.example.vkfuture.data.remote.retrofit.RetrofitApi
import retrofit2.Response
import javax.inject.Inject

class ProfileRepository @Inject constructor(private val retrofitApi: RetrofitApi) {
    suspend fun getProfileDetails() : Response<ProfileDetails> {
        return retrofitApi.getProfileDetails()
    }
}