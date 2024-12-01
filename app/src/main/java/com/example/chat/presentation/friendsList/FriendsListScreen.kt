package com.example.chat.presentation.friendsList

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import com.example.chat.presentation.common.Dialog
import com.example.chat.presentation.common.CircularProgress
import org.koin.androidx.compose.koinViewModel

@Composable
fun FriendsListScreen(
    navController: NavController,
    viewModel: FriendListViewModel = koinViewModel()
) {
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
    val friendListState = viewModel.friendListState.value

    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                viewModel.getFriendList()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    if (friendListState.error.isNotEmpty())
        Dialog(message = friendListState.error, confirmBtnText = "Login again") {
            viewModel.performLogout(navController)
        }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 10.dp, start = 10.dp, end = 10.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            Box(
                Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.Center),
                    text = "Chats",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )

                OutlinedButton(
                    modifier = Modifier
                        .align(Alignment.CenterEnd),
                    onClick = {
                        viewModel.performLogout(navController)
                    }) {
                    Text(text = "Logout")
                }
            }



            if (friendListState.data.isNotEmpty())
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 32.dp)
                ) {
                    val friendList = friendListState.data

                    friendList.forEach {
                        item {
                            FriendListItemRow(friendData = it, navController)
                        }
                    }
                }
        }

        if (friendListState.data.isEmpty()) {
            CircularProgress(isLoading = friendListState.isLoading)
        }
    }
}
