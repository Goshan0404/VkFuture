package com.example.vkfuture.ui.view.activity

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Bundle
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.vkfuture.R
import com.example.vkfuture.ui.model.BottomNavItem
import com.example.vkfuture.ui.theme.VkFutureTheme
import com.example.vkfuture.ui.view.navigation.BottomNavBar
import com.example.vkfuture.ui.view.navigation.Navigation
import com.vk.api.sdk.auth.VKScope.*
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute by remember { derivedStateOf { currentBackStackEntry?.destination?.route ?: "" } }
        Scaffold(topBar = {
            TopAppBar(title = {
                Text(getStringByName(currentRoute))
            })
        }, bottomBar = {
            BottomNavBar(
                items = listOf(
                    BottomNavItem(
                        name = getString(R.string.news),
                        route = "news",
                        icon = Icons.Filled.Home
                    ),
                    BottomNavItem(
                        name = getString(R.string.messages),
                        route = "messages",
                        icon = Icons.Filled.MailOutline
                    ),
                    BottomNavItem(
                        name = getString(R.string.other),
                        route = "other",
                        icon = Icons.Filled.MoreVert
                    )
                ),
                navController = navController,
                onItemClick = {
                    navController.navigate(it.route)
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

fun Context.getStringByName(_stringName: String): String {
    var result = ""
    var stringName = _stringName
    if (_stringName.contains("/")) {
        stringName = _stringName.substringBefore("/")
    }
    try {
        result = getString(resources.getIdentifier(stringName, "string", packageName))
    }
    catch(e: Resources.NotFoundException){
        result = "Хуй его знает где ты"
    }
    return result
}