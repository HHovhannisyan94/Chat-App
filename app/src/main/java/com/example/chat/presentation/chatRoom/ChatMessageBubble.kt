package com.example.chat.presentation.chatRoom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chat.domain.model.chatRoom.RoomHistoryList

@Composable
fun MessageBubble(
    message: RoomHistoryList.Message,
    isSender: Boolean

) {

    val radius =
        if (isSender) RoundedCornerShape(
            topStart = 16.dp,
            bottomStart = 16.dp,
            topEnd = 0.dp,
            bottomEnd = 16.dp
        ) else RoundedCornerShape(
            topStart = 0.dp,
            bottomStart = 16.dp,
            topEnd = 16.dp,
            bottomEnd = 16.dp
        )

    Column (
        modifier = Modifier
            .padding(bottom = 24.dp)

    ) {

        if (isSender.not()) {
            Time(message)
        }

        Text(
            modifier = Modifier
                .padding(top = 8.dp)
                .wrapContentSize(align = if (isSender) Alignment.CenterEnd else Alignment.CenterStart)
                .background(
                    color = if (isSender)
                        Color.Yellow
                    else
                        Color.Gray,
                    shape = radius
                )
                .padding(12.dp),
            text = message.textMessage.orEmpty(),
            color = if (isSender)
                Color.Black
            else
                Color.White,
            fontWeight = FontWeight.Normal
        )

        if (isSender) {
            Time(message)
        }

    }
}

@Composable
fun Time(
    message: RoomHistoryList.Message,
) {

    Column(
        modifier = Modifier
            .padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = message.formattedTime.orEmpty(),
            color = Color.Blue,
            style = MaterialTheme.typography.bodySmall
                .copy(
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
        )
    }
}
