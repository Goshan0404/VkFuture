package com.example.vkfuture.ui.view.messages

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vkfuture.R

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun MessagesScreen() {
    Column {// TODO: УБРАТЬ, ТЕСТОВАЯ ХУЙНЯ
        Text("Нам не дали доступ к Messages :(")
        HorizontalPager(pageCount = 3) {
            Image(painterResource(R.drawable.slava), "")
        }
    }

    Box(contentAlignment = Alignment.BottomEnd, modifier = Modifier.fillMaxSize(1f)) {
        FloatingActionButton(onClick = { /*TODO*/ }, Modifier.padding(16.dp)) {
            Icon(Icons.Filled.Add, "Создать диалог")
        }
    }
}