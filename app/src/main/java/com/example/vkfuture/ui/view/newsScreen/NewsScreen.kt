package com.example.vkfuture.ui.view.newsScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.vkfuture.data.model.modelnews.Group
import com.example.vkfuture.data.model.modelnews.Item
import com.example.vkfuture.data.model.modelnews.Profile
import com.example.vkfuture.ui.model.LoadState
import com.example.vkfuture.ui.stateholders.NewsViewModel
import com.example.vkfuture.ui.view.post.Post
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun NewsScreen(newsViewModel: NewsViewModel = viewModel(), navController: NavController) {

    val state by newsViewModel.loadState.collectAsState()
    val posts by newsViewModel.post.collectAsState()
    val profiles by newsViewModel.profiles.collectAsState()
    val groups by newsViewModel.groups.collectAsState()

    MaterialTheme {
        SetScreen(newsViewModel, state, posts, profiles, groups, navController)
    }

}

@Composable
private fun SetScreen(
    newsViewModel: NewsViewModel,
    state: LoadState,
    posts: List<Item>,
    profiles: HashMap<Int, Profile>,
    groups: HashMap<Int, Group>,
    navController: NavController
) {
    val refresh = rememberSwipeRefreshState(isRefreshing = false)
    SwipeRefresh(state = refresh, onRefresh = { newsViewModel.requestNews() }) {

        if (state.isLoading) {
            LoadingScreen()
        } else if (state.isError) {
            ErrorScreen()
        } else {
            SetPosts(posts, profiles, groups, newsViewModel, navController)
        }
    }

    AddPostButton(navController)
}

@Composable
private fun LoadingScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorScreen() {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Bottom
    ) {
        Snackbar(Modifier.padding(start = 5.dp, end = 5.dp, bottom = 30.dp)) {
            Text("Ошибка загрузки", fontSize = 22.sp)
        }
    }
}

@Composable
private fun AddPostButton(navController: NavController) {
    Box(contentAlignment = Alignment.BottomEnd, modifier = Modifier.fillMaxSize(1f)) {
        FloatingActionButton(onClick = { navController.navigate("createPost") }, Modifier.padding(16.dp)) {
            Icon(Icons.Filled.Add, "Добавить пост")
        }
    }
}

@Composable
private fun SetPosts(
    posts: List<Item>,
    profiles: HashMap<Int, Profile>,
    groups: HashMap<Int, Group>,
    newsViewModel: NewsViewModel,
    navController: NavController
) {
    LazyColumn(state = rememberLazyListState()) {
        items(posts) { post ->
            if (post.owner_id > 0) {
                PersonPost(post = post, profiles, newsViewModel, navController)
            } else {
                GroupPost(post = post, groups, newsViewModel, navController)
            }
        }
    }
}

@Composable
private fun PersonPost(post: Item, profiles: HashMap<Int, Profile>, newsViewModel: NewsViewModel, navController: NavController) {
    val profile = profiles[post.owner_id]
    val name = "${profile?.first_name}  ${profile?.last_name}"
    val image = profile?.photo_100
    Post(post = post, name = name, photo = image, newsViewModel, post.owner_id, navController)
}

@Composable
private fun GroupPost(post: Item, groups: HashMap<Int, Group>, newsViewModel: NewsViewModel, navController: NavController) {
    val group = groups[post.owner_id * -1]
    val name = group?.name
    val image = group?.photo_100
    Post(post = post, name = name, photo = image, newsViewModel, post.owner_id, navController)
}