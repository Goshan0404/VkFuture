package com.example.vkfuture.ui.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.example.vkfuture.utils.Constants
import com.example.vkfuture.utils.Token
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAuthenticationResult
import com.vk.api.sdk.auth.VKScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.TOKEN_DATA_STORE)

class AuthActivity : ComponentActivity() {


    private var token: String? = null
    private var userId: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch(Dispatchers.IO) {
            launch {
                token = getFromDataStore(Constants.ACCESS_TOKEN)
                userId = getFromDataStore(Constants.USER_ID)
            }.join()

            if (token == null || userId == null) {
                startAuthorization()
            } else {
                Token.accessToken = token as String
                Token.userId = userId as String
                startActivity(Intent(this@AuthActivity, MainActivity::class.java))
            }
        }
    }

    private suspend fun saveToDataStore(key: String, value: String) {
        dataStore.edit { settings ->
            val key = stringPreferencesKey(key)
            settings[key] = value
        }
    }

    private suspend fun getFromDataStore(key: String): String? {
        val keyDataStore = stringPreferencesKey(key)
        val value = dataStore.data
            .map { preferences ->
                preferences[keyDataStore]
            }.stateIn(lifecycleScope).value
        return value
    }

    private fun startAuthorization() {

        val authLauncher = VK.login(this) { result: VKAuthenticationResult ->
            when (result) {
                is VKAuthenticationResult.Success -> {
                    lifecycleScope.launch(Dispatchers.IO) {
                        launch {
                            token = result.token.accessToken
                            userId = result.token.userId.toString()
                            saveToDataStore(Constants.ACCESS_TOKEN, token!!)
                            saveToDataStore(Constants.USER_ID, userId!!)
                            Token.accessToken = result.token.accessToken
                            Token.userId = result.token.userId.toString()
                        }.join()

                        startActivity(Intent(this@AuthActivity, MainActivity::class.java))

                    }
                }

                is VKAuthenticationResult.Failed -> {
                    Log.d("VkException", "onCreate: ${result.exception}")
                    Toast.makeText(this, "Ошибка авторизации", Toast.LENGTH_SHORT).show()

                }
            }
        }
        authLauncher.launch(
            arrayListOf(
                VKScope.WALL,
                VKScope.PHOTOS,
                VKScope.FRIENDS,
                VKScope.OFFLINE
            )
        )
    }
}