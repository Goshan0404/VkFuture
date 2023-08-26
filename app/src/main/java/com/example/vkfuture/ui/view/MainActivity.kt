package com.example.vkfuture.ui.view

import android.content.Context
import android.content.SharedPreferences
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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.vkfuture.R
import com.example.vkfuture.data.model.modelnews.Token
import com.example.vkfuture.ui.model.BottomNavItem
import com.example.vkfuture.ui.stateholders.NewsViewModel
import com.example.vkfuture.ui.theme.VkFutureTheme
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAuthenticationResult
import com.vk.api.sdk.auth.VKScope
import com.vk.api.sdk.auth.VKScope.*
import okhttp3.internal.wait

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    private lateinit var prefManager: PreferenceManager
    private lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        prefManager = PreferenceManager(this)
        setContent {
            VkFutureTheme {
                // A surface container using the 'background' color from the theme
                setView()
            }
        }
    }



    @Composable
    private fun setView() {
        navController = rememberNavController()
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
            Navigation(
                navController = navController,
                paddingValues = paddingValues,
                activity = this
            )
        }
    }
}

class PreferenceManager(context: Context){
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("com.example.vkfuture", Context.MODE_PRIVATE)

    fun saveData(key: String, value: String){
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.commit()
    }

    fun getData(key: String, defaultValue: String): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }
}