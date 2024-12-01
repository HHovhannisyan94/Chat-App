package com.example.chat.presentation.chatRoom

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavHostController
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChatRoomScreen(
    navController: NavHostController,
    friendName: String,
    viewModel: ChatRoomViewModel = koinViewModel()
) {
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
    val chatState = viewModel.chatState.value
    val loggedInUser = viewModel.getUserInfo()

    LaunchedEffect(key1 = true) {
        viewModel.getChatHistory(friendName)
    }

    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                viewModel.connectToSocket(
                    sender = loggedInUser?.username.orEmpty(),
                    receiver = friendName
                )
            } else if (event == Lifecycle.Event.ON_STOP) {
                viewModel.disconnectSocket()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(1.dp)
    ) {

        Box(
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceContainer)
        ) {
            IconButton(
                modifier = Modifier.align(Alignment.CenterStart),
                onClick = { navController.popBackStack() },

                ) {
                Icon(
                    modifier = Modifier.padding(start = 8.dp),
                    imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
                    contentDescription = "Back button"
                )
            }

            Text(
                modifier = Modifier.align(Alignment.Center),
                text = friendName,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize(),
            reverseLayout = true
        ) {
            val groupByTimestampHistoryList = chatState.data.groupBy { it.formattedDate }

            groupByTimestampHistoryList.forEach { (date, messages) ->
                items(messages) { message ->
                    val isSender = message.sender == loggedInUser?.username
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = if (isSender)
                            Alignment.CenterEnd
                        else
                            Alignment.CenterStart
                    ) {
                        MessageBubble(
                            message = message,
                            isSender = isSender,
                        )
                    }
                }
                stickyHeader {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = date.orEmpty(),
                            style = MaterialTheme.typography.bodySmall
                                .copy(color = MaterialTheme.colorScheme.onBackground)
                        )
                    }
                }
            }
        }

        OutlinedCard(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            border = BorderStroke(
                width = 0.5.dp,
                MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f),
            )
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = viewModel.messageText.value,
                onValueChange = viewModel::onMessageChange,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences
                ),
                placeholder = {
                    Text(
                        text = "Message...",
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)
                    )
                },
                singleLine = false,
                maxLines = 20,

                trailingIcon = {
                    Icon(
                        modifier = Modifier
                            .clickable { viewModel.sendMessage() },
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Send message",
                        tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)
                    )
                }
            )
        }

    }
}