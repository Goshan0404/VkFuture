package com.example.vkfuture.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.vkfuture.BuildConfig
import com.example.vkfuture.R

import com.example.vkfuture.ui.theme.VkFutureTheme
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKScope
import com.vk.auth.main.VkClientUiInfo
import com.vk.superapp.SuperappKit
import com.vk.superapp.SuperappKitConfig
import com.vk.superapp.core.SuperappConfig

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VkFutureTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }



    }

    fun initSuperAppKit() {

        val appName = "VkFuture"

        // Укажите этот параметр и appId в файле ресурсов!

        val clientSecret = applicationContext.getString(R.string.vk_client_secret)

        // Укажите иконку, которая будет отображаться в компонентах пользовательского интерфейса

        val icon = AppCompatResources.getDrawable(applicationContext, R.mipmap.ic_launcher)!!

        val appInfo = SuperappConfig.AppInfo(
            appName,
            VK.getAppId(applicationContext).toString(),
            BuildConfig.VERSION_NAME
        )

        val config = SuperappKitConfig.Builder(application)
            // настройка VK ID
            .setAuthModelData(clientSecret)
            .setAuthUiManagerData(VkClientUiInfo(icon, appName))
            .setLegalInfoLinks(
                serviceUserAgreement = "https://id.vk.com/terms",
                servicePrivacyPolicy = "https://id.vk.com/privacy"
            )
            .setApplicationInfo(appInfo)

            // Получение Access token напрямую (без silentTokenExchanger)
            .setUseCodeFlow(true)

            .build()

        // Инициализация SuperAppKit
        SuperappKit.init(config)
    }

}




















@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    VkFutureTheme {
        Greeting("Android")
    }
}