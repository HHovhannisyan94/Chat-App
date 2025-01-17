package com.example.chat.presentation.common


class UsernameState :
    TextFieldState(validator = ::isUsernameValid, errorMessage = ::usernameErrorMessage)

class PasswordState :
    TextFieldState(validator = ::isPasswordValid, errorMessage = ::passwordErrorMessage)

private fun isPasswordValid(password: String): Boolean =
    password.length >= 6

private fun passwordErrorMessage(password: String) =
    if (password.isEmpty()) {
        "Password field is required."
    } else {
        "Password should be at least 6 characters."
    }

private fun isUsernameValid(username: String): Boolean =
    username.length >= 3

private fun usernameErrorMessage(username: String) =
    if (username.isEmpty()) {
        "Username field is required."
    } else {
        "Username should be at least 3 characters."
    }
