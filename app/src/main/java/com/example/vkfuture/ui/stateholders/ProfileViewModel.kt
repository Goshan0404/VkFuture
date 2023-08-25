package com.example.vkfuture.ui.stateholders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vkfuture.data.model.modelprofiledetails.ProfileDetails
import com.example.vkfuture.data.repository.ProfileRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Response

class ProfileViewModel : ViewModel() {
    val profileRepository = ProfileRepository()
    fun userAuthorisated(
        callBack : (
            bdate : String,
            city : String,
            country : String,
            firstName : String,
            lastName : String,
            sex : Int,
            status : String,
            screenName : String
                )-> Unit
    ){
        viewModelScope.launch{
            val response :Deferred<Response<ProfileDetails>> = async{
                profileRepository.getProfileDetails()
            }
            response.await()
            val result = response.getCompleted().body()!!.response
            callBack.invoke(
                result.bdate,
                result.city.title,
                result.country.title,
                result.first_name,
                result.last_name,
                result.sex,
                result.status,
                result.screen_name
            )
        }
    }

}