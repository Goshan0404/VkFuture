package com.example.vkfuture.ui.view.groupScreen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun GroupScreen(group_id: Int?, navController: NavController) {
    Log.e("Route", navController.currentDestination.toString())
    Column(Modifier.padding(12.dp)) {
        Text("Ну короче тут бы была страница группы $group_id")
    }
}