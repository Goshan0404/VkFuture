package com.example.vkfuture.ui.view

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAuthenticationResult
import com.vk.api.sdk.auth.VKScope

@Composable
fun Auth(activity: ComponentActivity, navController: NavController){
    val prefManager = PreferenceManager(activity)
    var token by remember { mutableStateOf(prefManager.getData("access_token", "")) }
    var user_id by remember { mutableStateOf<Long>(0) }
    if (token == "") {
        val authLauncher = VK.login(activity) { result: VKAuthenticationResult ->
            when (result) {
                is VKAuthenticationResult.Success -> {
                    prefManager.saveData("access_token", result.token.accessToken)
                    prefManager.saveData("user_id", result.token.userId.toString())
                    token = result.token.accessToken
                    user_id = result.token.userId.value
                }

                is VKAuthenticationResult.Failed -> {

                }
            }
        }
        authLauncher.launch(arrayListOf(VKScope.WALL, VKScope.PHOTOS, VKScope.FRIENDS))
    }
    else navController.navigate("news")
}