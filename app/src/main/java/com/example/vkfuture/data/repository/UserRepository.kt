package com.example.vkfuture.data.repository

import com.example.vkfuture.User
import com.example.vkfuture.data.retrofit.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Response

class UserRepository {
    val retrofitApi = RetrofitClient.retrofitApi
    fun getUserById(token: String, id: String, callBack: (user: User?) -> Unit) {

        CoroutineScope(Dispatchers.IO).launch {
            val response: Deferred<Response<User>> = async {
                retrofitApi.getUserById(token, id, "5.131")
            }

            response.await()
            if (response.getCompleted().isSuccessful) {
                callBack.invoke(response.getCompleted().body())
            }

        }
    }
}