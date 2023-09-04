package com.example.vkfuture.ui.view.navigation

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.vkfuture.ui.model.BottomNavItem
import com.example.vkfuture.ui.view.createPostScreen.CreatePostScreen
import com.example.vkfuture.ui.view.messagesScreen.MessagesScreen
import com.example.vkfuture.ui.view.newsScreen.NewsScreen
import com.example.vkfuture.ui.view.otherScreen.OtherScreen


@Composable
fun Navigation(navController: NavHostController, paddingValues: PaddingValues, activity: ComponentActivity) {
    NavHost(
        navController = navController,
        startDestination = "news",
        Modifier.padding(paddingValues)
    ) {
        composable("news") {
            NewsScreen(navController = navController)
        }
        composable("messages") {
            MessagesScreen()
        }
        composable("other") {
            OtherScreen(activity)
        }
        composable("createPost") {
            CreatePostScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavBar(
    items: List<BottomNavItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavItem) -> Unit
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    NavigationBar(modifier = modifier) {
        items.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            NavigationBarItem(selected = selected, onClick = { onItemClick(item) }, label = { Text(item.name) }, icon = {
                Column(horizontalAlignment = CenterHorizontally) {
                    if (item.badgeCount > 0) {
                        BadgedBox(badge = { Badge { Text(item.badgeCount.toString()) } }) {
                            Icon(imageVector = item.icon, contentDescription = item.name)
                        }
                    } else {
                        Icon(imageVector = item.icon, contentDescription = item.name)
                    }
                }
            })
        }
    }
}