package com.example.chat.presentation.auth.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.chat.common.navigateTo
import com.example.chat.presentation.common.AnnotatedClickableText
import com.example.chat.presentation.common.ErrorText
import com.example.chat.presentation.common.CircularProgress
import com.example.chat.presentation.common.Routes
import com.example.chat.presentation.common.TextFieldWithError
import org.koin.androidx.compose.koinViewModel


@Composable
fun LoginScreen(navController: NavHostController, viewModel: LoginViewModel = koinViewModel()) {

    val loginState = viewModel.loginState.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        if (loginState.data != null)
            LaunchedEffect(key1 = true) {
                navigateTo(
                    navController = navController,
                    destination = Routes.FriendsList.route,
                    clearBackStack = true
                )
            }

        Column(
            modifier = Modifier.fillMaxSize()
        ) {


            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 20.dp, start = 40.dp, end = 40.dp, bottom = 60.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val passwordVisible = remember { mutableStateOf(false) }

                TextFieldWithError(
                    label = "Username",
                    value = viewModel.usernameState.text,
                    keyboardType = KeyboardType.Text,
                    isError = viewModel.usernameState.error != null,
                    errorMessage = viewModel.usernameState.error,
                    onValueChange = {
                        viewModel.onUsernameChanged(it)
                        viewModel.usernameState.validate()
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))

                TextFieldWithError(
                    label = "Password",
                    value = viewModel.passwordState.text,
                    keyboardType = KeyboardType.Password,
                    isError = viewModel.passwordState.error != null,
                    errorMessage = viewModel.passwordState.error,
                    onValueChange = {
                        viewModel.onPasswordChanged(it)
                        viewModel.passwordState.validate()
                    },
                    trailingIcon = {
                        val eyeIcon = if (passwordVisible.value) Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff

                        val contentDescription =
                            if (passwordVisible.value) "Hide password" else "Show password"
                        IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                            Icon(imageVector = eyeIcon, contentDescription = contentDescription)
                        }
                    },
                    visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                )

                Spacer(modifier = Modifier.height(20.dp))


                if (loginState.error.isNotBlank())
                    ErrorText(loginState.error)

                Spacer(modifier = Modifier.height(20.dp))

                Button(modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                    onClick = {
                        viewModel.performLogin()
                    }) {
                    Text(text = "Login in", color = MaterialTheme.colorScheme.onSecondaryContainer)
                }
            }

            AnnotatedClickableText(
                text = "Don't have account? ",
                clickableText = "Sign Up",
                route = Routes.SignUp.route,
                navController = navController
            )


        }

    }
    CircularProgress(isLoading = loginState.isLoading)
}

@Preview(showBackground = true)
@Composable
fun ScreenLoginPrev() {
    LoginScreen(rememberNavController())
}