package com.example.chat.presentation.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.chat.common.navigateTo



@Composable
fun AnnotatedClickableText(
    text: String,
    clickableText: String,
    route: String? = null,
    onClick: () -> Unit = {},
    navController: NavHostController
) {
    val annotatedText = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color.Gray)) { append(text) }
        pushStringAnnotation(tag = clickableText, annotation = clickableText)
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
            append(clickableText)
        }
        pop()
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Text(
            text = annotatedText,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.clickable {
                annotatedText.getStringAnnotations(
                    tag = clickableText,
                    start = 0,
                    end = annotatedText.length
                ).firstOrNull()?.let {
                    if (route != null) {
                        navigateTo(navController, route)
                    } else {
                        onClick()
                    }
                }
            }
        )
    }
}


@Composable
fun TextFieldWithError(
    label: String,
    value: String,
    keyboardType: KeyboardType,
    isError: Boolean = false,
    errorMessage: String? = "",
    onValueChange: (String) -> Unit,
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    Column {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = label) },
            value = value,
            onValueChange = { onValueChange(it) },
            isError = isError,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),

            shape = MaterialTheme.shapes.small,
            singleLine = true,
            trailingIcon = trailingIcon,
            visualTransformation = visualTransformation,
        )
        if (isError) {
            ErrorText(errorMessage)
        }
    }
}

@Composable
fun ErrorText(errorMessage: String?) {
    Text(
        modifier = Modifier.padding(top = 4.dp, start = 16.dp),
        text = errorMessage ?: "",
        color = MaterialTheme.colorScheme.error,
        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.error)
    )
}

@Composable
fun CircularProgress(isLoading: Boolean) {
    if (isLoading) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun Dialog(
    title: String = "Error!",
    message: String,
    confirmBtnText: String,
    confirmBtnClick: () -> Unit
) {
    AlertDialog(
        title = { Text(text = title) },
        text = { Text(text = message) },
        onDismissRequest = { },
        confirmButton = {
            TextButton(onClick = { confirmBtnClick() }) {
                Text(text = confirmBtnText)
            }
        })
}



