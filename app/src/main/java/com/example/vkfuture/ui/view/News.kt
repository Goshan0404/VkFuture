package com.example.vkfuture.ui.view

import android.graphics.drawable.Drawable
import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.InspectableModifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vkfuture.R
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment


data class PostObj(val name: String, val time: String, val text: String, val image_id: Int)

@Preview
@Composable
fun News() {
    val posts = listOf(
        PostObj("Георгий Чернихов", "7 минут назад", "Бля я не вывожу", R.drawable.gosha),
        PostObj("Слава Шептихин", "15 минут назад", "В рот ебал я этот Compose", R.drawable.slava),
        PostObj(
            "JetPack Compose",
            "постоянно",
            "В рот я вас ебал)))))))",
            R.drawable.ic_launcher_foreground
        ),
        PostObj("Георгий Чернихов", "7 минут назад", "Бля я не вывожу", R.drawable.gosha),
        PostObj("Слава Шептихин", "15 минут назад", "В рот ебал я этот Compose", R.drawable.slava),
        PostObj(
            "JetPack Compose",
            "постоянно",
            "В рот я вас ебал)))))))",
            R.drawable.ic_launcher_foreground
        ),
        PostObj("Георгий Чернихов", "7 минут назад", "Бля я не вывожу", R.drawable.gosha),
        PostObj("Слава Шептихин", "15 минут назад", "В рот ебал я этот Compose", R.drawable.slava),
        PostObj(
            "JetPack Compose",
            "постоянно",
            "В рот я вас ебал)))))))",
            R.drawable.ic_launcher_foreground
        )
    )

    LazyColumn {
        items(posts) { post ->
            Post(post = post)
        }
    }
}

@Preview
@Composable
fun PostPreview() {
    Post(PostObj("Георгий Чернихов", "7 минут назад", "Бля я не вывожу", R.drawable.gosha))
}

@Composable
fun Post(post: PostObj) {
    MaterialTheme {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(size = 12.dp)
                )
                .padding(4.dp) // TODO: ПОДУМАТЬ
        ) {
            Row(
                modifier = Modifier
                    .padding(12.dp)
                    .height(48.dp)
            ) {
                Image(
                    painter = painterResource(id = post.image_id),
                    contentDescription = "Avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape),
                )

                Column(
                    Modifier
                        .fillMaxHeight()
                        .padding(start = 8.dp), verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        post.name,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp
                    )
                    Text(
                        post.time,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 12.sp
                    )
                }
                Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.TopEnd) {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Filled.MoreVert, contentDescription = "Больше")
                    }
                }


            }
            Text(post.text)
            Row {
                FilledIconButton(onClick = { /*TODO*/ }) {

                }
                FilledIconButton(onClick = { /*TODO*/ }) {

                }
            }
        }
    }
}