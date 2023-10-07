package com.example.vkfuture.ui.view.navigation

sealed class Screen(val route: String) {
    object NewsScreen: Screen("news_screen")
    object MessagesScreen: Screen("message_screen")
    object CreatePostScreen: Screen("create_post_screen")
    object OtherScreen: Screen("other_screen")
}
