package com.example.vkfuture.ui.view.newsScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.vkfuture.data.local.entity.PostEntity
import com.example.vkfuture.ui.view.Post
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun NewsScreen(newsViewModel: NewsViewModel = hiltViewModel(), navController: NavController) {

    val posts = newsViewModel.post.collectAsLazyPagingItems()
    val loadingState = posts.loadState

    MaterialTheme {
        SetScreen(newsViewModel, loadingState, posts, navController)
    }
}

@Composable
private fun SetScreen(
    newsViewModel: NewsViewModel,
    loadingState: CombinedLoadStates,
    posts: LazyPagingItems<PostEntity>,
    navController: NavController,
) {
    val refresh = rememberSwipeRefreshState(isRefreshing = false)
    SwipeRefresh(state = refresh, onRefresh = { newsViewModel.requestNews() }) {

        if (loadingState.refresh is LoadState.Loading) {
            LoadingScreen()
        } else if (loadingState.refresh is LoadState.Error) {
            ErrorScreen()
        } else {
            SetPosts(posts, newsViewModel, navController)
            AddPostButton(navController)
        }
    }
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
        FloatingActionButton(
            onClick = { navController.navigate("createPost") },
            Modifier.padding(16.dp)
        ) {
            Icon(Icons.Filled.Add, "Добавить пост")
        }
    }
}

@Composable
private fun SetPosts(
    posts: LazyPagingItems<PostEntity>,
    newsViewModel: NewsViewModel,
    navController: NavController,
) {

    LazyColumn(
        contentPadding = PaddingValues(
            all = 5.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(count = posts.itemCount,
            key = { posts[it]!!.postId },
            itemContent = { index ->
                val postData = checkNotNull(posts[index])
                Post(post = postData, newsViewModel, navController)
            })
    }
}