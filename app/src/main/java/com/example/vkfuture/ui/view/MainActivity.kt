package com.example.vkfuture.ui.view

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.vkfuture.data.model.BottomNavItem
import com.example.vkfuture.ui.stateholders.NewsViewModel
import com.example.vkfuture.ui.theme.VkFutureTheme
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAuthenticationResult
import com.vk.api.sdk.auth.VKScope.*

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    private lateinit var newsViewModel: NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newsViewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        setContent {
            VkFutureTheme {
                // A surface container using the 'background' color from the theme
                setView()
            }
        }
        authorization()
    }

    @Composable
    private fun setView() {
        val navController = rememberNavController()
        var title by remember { mutableStateOf("Новости") }
        Scaffold(topBar = {
            TopAppBar(title = {
                Text(title)
            })
        }, bottomBar = {
            BottomNavBar(
                items = listOf(
                    BottomNavItem(
                        name = "Новости",
                        route = "news",
                        icon = Icons.Filled.Home
                    ),
                    BottomNavItem(
                        name = "Сообщения",
                        route = "messages",
                        icon = Icons.Filled.MailOutline
                    ),
                    BottomNavItem(
                        name = "Другое",
                        route = "other",
                        icon = Icons.Filled.MoreVert
                    )
                ),
                navController = navController,
                onItemClick = {
                    navController.navigate(it.route)
                    title = it.name
                    Log.d("TITLE_CHANGE", title)
                }
            )
        }) { paddingValues ->
            Navigation(navController = navController, paddingValues = paddingValues)
        }
    }

    private fun authorization() {
        val authLauncher = VK.login(this) { result: VKAuthenticationResult ->
            when (result) {
                is VKAuthenticationResult.Success -> {
                    newsViewModel.userAuthorizated()
                }

                is VKAuthenticationResult.Failed -> {

                }
            }
        }
        authLauncher.launch(arrayListOf(WALL, PHOTOS, FRIENDS))
    }
}