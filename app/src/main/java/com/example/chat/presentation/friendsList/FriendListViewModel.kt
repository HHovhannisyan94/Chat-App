package com.example.chat.presentation.friendsList

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.chat.common.DataStoreHelper
import com.example.chat.common.ResponseResource
import com.example.chat.common.navigateTo
import com.example.chat.domain.usecase.FriendListUseCase
import com.example.chat.presentation.common.Routes
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class FriendListViewModel(
    private val useCase: FriendListUseCase,
    private val dataStoreHelper: DataStoreHelper
) : ViewModel() {

    private val _friendListState = mutableStateOf(FriendListState())
    val friendListState: State<FriendListState> = _friendListState

    fun performLogout(navController: NavController) {
        runBlocking {
            dataStoreHelper.clearSession()
        }
        navigateTo(navController, Routes.Login.route, true)
    }

    fun getFriendList() {
        _friendListState.value = FriendListState(isLoading = true)
        viewModelScope.launch {
            useCase().onEach {
                when (it) {
                    is ResponseResource.Error ->
                        _friendListState.value =
                            FriendListState(error = it.errorMessage.errorMessage.orEmpty())
                    is ResponseResource.Success ->
                        _friendListState.value =
                            FriendListState(data = it.data.friendInfo.orEmpty())
                }
            }.launchIn(viewModelScope)
        }
    }
}