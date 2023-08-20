//package com.example.vkfuture.ui.stateholders
//
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import com.example.vkfuture.data.model.User
//import com.example.vkfuture.data.repository.UserRepository
//
//class UserViewModel : ViewModel() {
//    var user = MutableLiveData<User>()
//    private val repository = UserRepository()
//
//    fun getUser(id: Int) {
//        repository.getUserById(id) {
//            user.value = it
//        }
//    }
//
//}