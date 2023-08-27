package com.example.vkfuture.ui.view

import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import coil.compose.AsyncImage
import com.example.vkfuture.R
import com.example.vkfuture.ui.stateholders.ProfileViewModel

@Composable
fun Other(activity: ComponentActivity) {
    val profileViewModel: ProfileViewModel = ViewModelProvider(activity)[ProfileViewModel::class.java]
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var id by remember { mutableStateOf("") }
    profileViewModel.userAuthorisated{response ->
        name = "${response.first_name} ${response.last_name}"
        phone = response.phone
        id = response.screen_name
    }

    LazyColumn {
        item {
            Row(
                modifier = Modifier
                    .padding(12.dp)
                    .height(56.dp)
                    .clickable(onClick = {})
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.slava),
                    contentDescription = "Avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape),
                )

                Column(
                    Modifier
                        .fillMaxHeight()
                        .padding(start = 8.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        name ?: "",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Medium,
                        fontSize = 24.sp,
                        maxLines = 1
                    )
                    Text(
                        phone,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}