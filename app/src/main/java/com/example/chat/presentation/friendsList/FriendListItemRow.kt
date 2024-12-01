package com.example.chat.presentation.friendsList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.chat.common.getTimeAgo
import com.example.chat.common.navigateTo
import com.example.chat.domain.model.friendList.FriendList
import com.example.chat.presentation.common.Routes

@Composable
fun FriendListItemRow(friendData: FriendList.FriendInfo, navController: NavController) {
    val args = friendData.username

    Row(
        Modifier
            .fillMaxWidth()
            .clickable {
                navigateTo(
                    navController = navController,
                    "${Routes.ChatRoom.route}/$args"
                )
            }
            .height(60.dp)
    ) {

        Column(
            modifier = Modifier
                .weight(0.7f)
                .padding(start = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = friendData.username,
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = if (friendData.lastMessage?.timestamp != null) getTimeAgo(friendData.lastMessage.timestamp) else "",
                    style = MaterialTheme.typography.labelSmall
                )
            }

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 40.dp),
                text = friendData.lastMessage?.textMessage ?: "...",
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 11.sp
                ),
                maxLines = 1
            )
        }
    }
}