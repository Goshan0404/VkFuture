package com.example.vkfuture.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.vkfuture.utils.Token
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAuthenticationResult
import com.vk.api.sdk.auth.VKScope

class AuthActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefManager = PreferenceManager(this)
        var token = prefManager.getData("access_token", "")
        var user_id = prefManager.getData("user_id", "")

        if (token == "") {
            val authLauncher = VK.login(this) { result: VKAuthenticationResult ->
                when (result) {
                    is VKAuthenticationResult.Success -> {
                        prefManager.saveData("access_token", result.token.accessToken)
                        prefManager.saveData("user_id", result.token.userId.toString())
                        token = result.token.accessToken
                        user_id = result.token.userId.value.toString()
                        Token.setToken(token, user_id)
                        startActivity(Intent(this, MainActivity::class.java))
                    }
                    is VKAuthenticationResult.Failed -> {

                    }
                }
            }
            authLauncher.launch(arrayListOf(VKScope.WALL, VKScope.PHOTOS, VKScope.FRIENDS))
        } else {
            Token.setToken(token, user_id)
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}