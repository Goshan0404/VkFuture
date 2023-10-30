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
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.example.vkfuture.utils.Constants
import com.example.vkfuture.utils.Token
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAuthenticationResult
import com.vk.api.sdk.auth.VKScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AuthActivity : ComponentActivity() {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.tokenDataStore)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val token: LiveData<String> = dataStore.data.map {
            it[stringPreferencesKey(Constants.accessToken)] ?: ""
        }.asLiveData()

        val userId: LiveData<String> = dataStore.data.map {
            it[stringPreferencesKey(Constants.userId)] ?: ""
        }.asLiveData()

        token.observe(this) { tokenData ->
            userId.observe(this) { idData ->
                if (tokenData != "" && idData != "") {
                    Token.setToken(tokenData, idData)
                    startActivity(Intent(this@AuthActivity, MainActivity::class.java))
                }
            }
        }

        if (token.value != null && userId.value != null) {
            Token.setToken(checkNotNull(token.value), checkNotNull(userId.value))
            startActivity(Intent(this@AuthActivity, MainActivity::class.java))
        } else {
            startAuthorization(token, userId)
        }
    }

    private suspend fun saveToDataStore(key: String, value: String) {
        dataStore.edit { settings ->
            val key = stringPreferencesKey(key)
            settings[key] = value
        }
    }

    private suspend fun getFromDataStore(key: String): Flow<String?> {
        val keyDataStore = stringPreferencesKey(key)
        val value: Flow<String?> = dataStore.data
            .map { preferences ->
                preferences[keyDataStore]
            }
        return value
    }

    private fun startAuthorization(token: LiveData<String>, userId: LiveData<String>) {

        val authLauncher = VK.login(this) { result: VKAuthenticationResult ->
            when (result) {
                is VKAuthenticationResult.Success -> {
                    lifecycleScope.launch(Dispatchers.Main) {
                        saveToDataStore(Constants.accessToken, result.token.accessToken)
                        saveToDataStore(Constants.userId, result.token.userId.toString())
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