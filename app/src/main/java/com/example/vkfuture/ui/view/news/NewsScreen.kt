package com.example.vkfuture.ui.view.news

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.vkfuture.data.model.modelnews.Group
import com.example.vkfuture.data.model.modelnews.Item
import com.example.vkfuture.data.model.modelnews.Profile
import com.example.vkfuture.ui.stateholders.NewsViewModel
import kotlinx.coroutines.flow.collect
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/*var profiles = HashMap<Int, Profile>()
var groups = HashMap<Int, Group>()*/

@Composable
fun NewsScreen(activity: ComponentActivity) {
    val newsViewModel = viewModel<NewsViewModel>()
    val state by newsViewModel.state.collectAsState()
    val posts by newsViewModel.post.collectAsState()
    val profiles by newsViewModel.profiles.collectAsState()
    val groups by newsViewModel.groups.collectAsState()
    newsViewModel.requestNews()

    if (state.isLoadedRemote) {
        LazyColumn {
            items(posts) { post ->
                if (post.owner_id > 0) {
                    PersonPost(post = post, profiles)
                } else {
                    GroupPost(post = post, groups)
                }
            }
        }
    }
}

@Composable
private fun PersonPost(post: Item, profiles: HashMap<Int, Profile>) {
    val profile = profiles[post.owner_id]
    val name = "${profile?.first_name}  ${profile?.last_name}"
    val image = profile?.photo_100
    Post(post = post, name = name, photo = image)
}

@Composable
private fun GroupPost(post: Item, groups: HashMap<Int, Group>) {
    val group = groups[post.owner_id * -1]
    val name = group?.name
    val image = group?.photo_100
    Post(post = post, name = name, photo = image)
}

@Composable
private fun Post(post: Item, name: String?, photo: String?) {
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
                AsyncImage(
                    /* painter = painterResource(id = R.drawable.ic_launcher_foreground),*/
                    model = photo,
                    contentDescription = "Avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(48.dp)
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
                        fontSize = 16.sp,
                        maxLines = 1
                    )
                    Text(
                        convertUnix(post.date),
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
            Text(
                post.text ?: "",
                modifier = Modifier.padding(start = 12.dp),
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 16.sp
            )
            Row(Modifier.padding(12.dp)) {
                TextIconButton(
                    post.likes?.count.toString(),
                    Icons.Outlined.FavoriteBorder
                ) { /*TODO*/ }
                Spacer(Modifier.width(8.dp))
                TextIconButton(
                    post.comments?.count.toString(),
                    Icons.Outlined.MailOutline
                ) { /*TODO*/ }
                Spacer(Modifier.width(8.dp))
                TextIconButton(
                    post.reposts?.count.toString(),
                    Icons.Outlined.Send
                ) { /*TODO*/ }
                Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                    Row {
                        Icon(Icons.Filled.Person, "Просмотры")
                        Text(post.views?.count.toString())
                    }
                }
            }
        }
    }
}

@Composable
private fun TextIconButton(
    text: String, icon: ImageVector, onClick: () -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        modifier = Modifier.clickable(onClick = onClick),
        shape = RoundedCornerShape(100.dp)
    ) {
        Row(Modifier.padding(4.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = icon, contentDescription = text, Modifier.padding(4.dp))
            Text(text, fontSize = 18.sp, modifier = Modifier.padding(end = 4.dp))
        }
    }
}

private fun convertUnix(time: Int): String {
    val date = Date(time.toLong() * 1000)
    val dateFormat = SimpleDateFormat("dd MMMM kk:mm", Locale.getDefault())
    return dateFormat.format(date)
}

@Preview
@Composable
fun BottomPreview(){
    Row(Modifier.padding(12.dp)) {
        TextIconButton(
            "15",
            Icons.Outlined.FavoriteBorder
        ) { /*TODO*/ }
        Spacer(Modifier.width(8.dp))
        TextIconButton(
            "258",
            Icons.Outlined.MailOutline
        ) { /*TODO*/ }
        Spacer(Modifier.width(8.dp))
        TextIconButton(
            "175",
            Icons.Outlined.Send
        ) { /*TODO*/ }
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(44.dp), contentAlignment = Alignment.CenterEnd) {
            Row() {
                Icon(Icons.Filled.Person, "Просмотры")
                Text("2556")
            }
        }
    }
}