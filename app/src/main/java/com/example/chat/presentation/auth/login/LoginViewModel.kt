package com.example.chat.presentation.auth.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chat.common.DataStoreHelper
import com.example.chat.common.ResponseResource
import kotlinx.coroutines.launch

import com.example.chat.data.remote.dto.login.request.LoginRequestDto
import com.example.chat.domain.mapper.toUser
import com.example.chat.domain.usecase.LoginUseCase
import com.example.chat.presentation.common.PasswordState
import com.example.chat.presentation.common.UsernameState

class LoginViewModel(
    private val useCase: LoginUseCase,
    private val dataStoreHelper: DataStoreHelper
) : ViewModel() {

    private val _usernameState = UsernameState()
    val usernameState: UsernameState = _usernameState

    private val _passwordState = PasswordState()
    val passwordState: PasswordState = _passwordState

    private val _loginState = mutableStateOf(LoginState())
    val loginState: State<LoginState> = _loginState

    fun onUsernameChanged(username: String) {
        _usernameState.text = username
    }

    fun onPasswordChanged(password: String) {
        _passwordState.text = password
    }

    fun performLogin() {
        if (validateInputs())
            viewModelScope.launch {
                _loginState.value = LoginState(isLoading = true)
                val request = LoginRequestDto(
                    username = usernameState.text, password = passwordState.text
                )
                useCase(request).collect {
                    when (it) {
                        is ResponseResource.Error -> _loginState.value =
                            LoginState(error = it.errorMessage.errorMessage.orEmpty())
                        is ResponseResource.Success -> {
                            dataStoreHelper.saveUser(it.data.toUser().copy(isLoggedIn = true))
                            _loginState.value =
                                LoginState(data = it.data)
                        }
                    }
                }
            }
    }

    private fun validateInputs(): Boolean {
        if (usernameState.text.isEmpty()) {
            _usernameState.validate()
            return false
        }
        if (passwordState.text.isEmpty()) {
            _passwordState.validate()
            return false
        }
        return true
    }
}