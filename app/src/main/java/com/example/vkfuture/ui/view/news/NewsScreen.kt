package com.example.vkfuture.ui.view.news

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.vkfuture.R
import com.example.vkfuture.data.model.modelnews.Group
import com.example.vkfuture.data.model.modelnews.Item
import com.example.vkfuture.data.model.modelnews.Profile
import com.example.vkfuture.ui.model.LoadState
import com.example.vkfuture.ui.stateholders.NewsViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun NewsScreen() {
    val newsViewModel = viewModel<NewsViewModel>()
    val state by newsViewModel.loadState.collectAsState()
    val posts by newsViewModel.post.collectAsState()
    val profiles by newsViewModel.profiles.collectAsState()
    val groups by newsViewModel.groups.collectAsState()

    setScreen(newsViewModel, state, posts, profiles, groups)

}



@Composable
private fun setScreen(
    newsViewModel: NewsViewModel,
    state: LoadState,
    posts: List<Item>,
    profiles: HashMap<Int, Profile>,
    groups: HashMap<Int, Group>
) {
    val refresh = rememberSwipeRefreshState(isRefreshing = false)
    SwipeRefresh(state = refresh, onRefresh = { newsViewModel.requestNews() }) {
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            SetPosts(posts, profiles, groups, newsViewModel)
        }
    }

    setAddPostButton()
}

@Composable
private fun setAddPostButton() {
    Box(contentAlignment = Alignment.BottomEnd, modifier = Modifier.fillMaxSize(1f)) {
        FloatingActionButton(onClick = { /*TODO*/ }, Modifier.padding(16.dp)) {
            Icon(Icons.Filled.Add, "Добавить пост")
        }
    }
}

@Composable
private fun SetPosts(
    posts: List<Item>,
    profiles: HashMap<Int, Profile>,
    groups: HashMap<Int, Group>,
    newsViewModel: NewsViewModel
) {
    LazyColumn(state = rememberLazyListState()) {
        items(posts) { post ->
            if (post.owner_id > 0) {
                PersonPost(post = post, profiles, newsViewModel)
            } else {
                GroupPost(post = post, groups, newsViewModel)
            }
        }
    }
}

@Composable
private fun PersonPost(post: Item, profiles: HashMap<Int, Profile>, newsViewModel: NewsViewModel) {
    val profile = profiles[post.owner_id]
    val name = "${profile?.first_name}  ${profile?.last_name}"
    val image = profile?.photo_100
    Post(post = post, name = name, photo = image, newsViewModel)
}

@Composable
private fun GroupPost(post: Item, groups: HashMap<Int, Group>, newsViewModel: NewsViewModel) {
    val group = groups[post.owner_id * -1]
    val name = group?.name
    val image = group?.photo_100
    Post(post = post, name = name, photo = image, newsViewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Post(post: Item, name: String?, photo: String?, newsViewModel: NewsViewModel) {
    var isDropdownState by remember { mutableStateOf(false) }
    var isBottomSheetVisible by remember { mutableStateOf(false) }
    var isUserLiked by remember { mutableStateOf(post.likes.user_likes) }
    var likesCount by remember { mutableStateOf(post.likes.count) }
    MaterialTheme {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    //shape = RoundedCornerShape(size = 12.dp)
                )
                .padding(4.dp)
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
                        maxLines = 1,
                        modifier = Modifier.widthIn(max = 224.dp)
                    )
                    Text(
                        convertUnix(post.date),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 12.sp
                    )
                }
                Box(
                    Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minWidth = 24.dp), contentAlignment = Alignment.TopEnd
                ) {
                    IconButton(onClick = {
                        isDropdownState = true; Log.d(
                        "dropdownstate",
                        isDropdownState.toString()
                    )
                    }) {
                        Icon(Icons.Filled.MoreVert, contentDescription = "Больше")
                    }
                    DropdownMenu(
                        expanded = isDropdownState,
                        onDismissRequest = { isDropdownState = false }) {
                        DropdownMenuItem(text = { Text("А ПОЧЕМУ") }, onClick = { /*TODO*/ })
                        DropdownMenuItem(text = { Text("Я ВСЕГДА") }, onClick = { /*TODO*/ })
                        DropdownMenuItem(text = { Text("ДЕЛАЮ ВСЁ") }, onClick = { /*TODO*/ })
                        DropdownMenuItem(text = { Text("ПОЗДНО НОЧЬЮ") }, onClick = { /*TODO*/ })
                    }
                }


            }
            Text(
                post.text ?: "",
                modifier = Modifier.padding(start = 12.dp, end = 12.dp),
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 16.sp
            )



            Row(Modifier.padding(12.dp)) {
                TextIconButton(
                    likesCount.toString(),
                    Icons.Outlined.FavoriteBorder,
                    isUserLiked == 1
                ) {
                    if (isUserLiked == 1) {isUserLiked = 0; likesCount -= 1; post.likes.user_likes = 0; post.likes.count -=1}
                    else {isUserLiked = 1; likesCount += 1; post.likes.user_likes = 1; post.likes.count += 1}

                    newsViewModel.userLikeChanged(isUserLiked,
                        post.type,
                        post.id.toString(),
                        post.owner_id.toString())
                }

                Spacer(Modifier.width(8.dp))

                TextIconButton(
                    post.comments.count.toString(),
                    Icons.Outlined.MailOutline
                ) { Log.d("PICTURES", post.attachments.toString()) }

                Spacer(Modifier.width(8.dp))

                TextIconButton(
                    post.reposts.count.toString(),
                    Icons.Outlined.Send
                ) { isBottomSheetVisible = true }
                Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                    Row(Modifier.padding(12.dp)) {
                        Icon(Icons.Filled.Person, "Просмотры")
                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                        Text(post.views.count.toString())
                    }
                }
            }
        }
        if (isBottomSheetVisible) {
            ModalBottomSheet(onDismissRequest = { isBottomSheetVisible = false }, Modifier.padding()) {
                TextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Поиск диалогов") },
                    leadingIcon = { Icon(Icons.Filled.Search, "Поиск диалогов") })
                LazyRow() {
                    items(5) {
                        Image(
                            painter = painterResource(id = R.drawable.gosha),
                            contentDescription = "Avatar",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape),
                        )
                    }
                }
                TextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Сообщение") },
                    leadingIcon = { Icon(Icons.Outlined.MailOutline, "Сообщение") })
                LazyRow() {
                    items(5) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_launcher_foreground),
                            contentDescription = "Avatar",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape),
                        )
                    }
                }
            }
        }
    }
}


@Composable
private fun TextIconButton(
    text: String,
    icon: ImageVector,
    used: Boolean = false,
    onClick: () -> Unit
) {

    FilledTonalButton(
        onClick = { onClick.invoke()
                  },
        Modifier.defaultMinSize(minWidth = 1.dp, minHeight = 1.dp),
        contentPadding = PaddingValues(8.dp),
        colors = if (used) ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onSecondaryContainer) else ButtonDefaults.buttonColors()
    ) {
        Icon(icon, "", Modifier.size(ButtonDefaults.IconSize))
        Spacer(Modifier.size(4.dp))
        Text(text, maxLines = 1)
    }

}

private fun convertUnix(time: Int): String { // TODO: ВЫНЕСТИ КУДА-НИБУДЬ
    val date = Date(time.toLong() * 1000)
    val dateFormat = SimpleDateFormat("dd MMMM kk:mm", Locale.getDefault())
    return dateFormat.format(date)
}

@Composable
fun repostBottomSheet() { // TODO: СДЕЛАТЬ

}