package com.endcodev.beautifullogin.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.endcodev.beautifullogin.R
import com.endcodev.beautifullogin.data.FirebaseAuth
import com.endcodev.beautifullogin.data.FirebaseClient
import com.endcodev.beautifullogin.domain.model.HomeUiState
import com.endcodev.beautifullogin.presentation.utils.UiText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomeViewModel : ViewModel(), KoinComponent {
    private val client: FirebaseClient by inject()
    private val auth: FirebaseAuth by inject()
    private val _state = MutableStateFlow(HomeUiState())
    val state = _state.asStateFlow()

    private val _errorChannel = Channel<UiText>()
    val errorChannel = _errorChannel.receiveAsFlow()

    private fun triggerAlert(error: UiText.StringResource) {
        viewModelScope.launch {
            _errorChannel.send(error)
        }
    }

    init {
        val client = client.auth.currentUser
        _state.update {
            it.copy(
                email = client?.email ?: "no mail",
                userName = client?.displayName ?: "no name",
                image = client?.photoUrl,
                phone = client?.phoneNumber?: "no phone",
            )
        }
    }

    fun onMailChanged(mail: String) {
        _state.update { it.copy(email = mail) }
    }

    fun onUserNameChanged(name: String) {
        _state.update { it.copy(userName = name) }
    }

    fun logOut(onComplete: () -> Unit) {
        viewModelScope.launch {
            triggerAlert(UiText.StringResource(resId = R.string.disconnecting))
            delay(1000)
            auth.disconnectUser()
            onComplete()
        }
    }

    fun deleteAccount() {
        //todo verify delete with Dialog
        auth.deleteAccount(onComplete = {
            if (it == 0)
                triggerAlert(UiText.StringResource(resId = R.string.account_deleted))
            else
                triggerAlert(UiText.StringResource(resId = R.string.error_deleting_account))
        })
    }

    fun saveNewInfo() {
        auth.changeUserData(
            newUser = _state.value.userName,
            onComplete = {
                if (it == 0)
                    triggerAlert(UiText.StringResource(resId = R.string.user_data_change))
                else
                    triggerAlert(UiText.StringResource(resId = R.string.error_data_change))
            }
        )
    }

    fun changeEditMode() {
        _state.update { it.copy(editMode = !it.editMode) }

        CoroutineScope(Dispatchers.IO).launch {
            val currentUser = client.auth.currentUser
            currentUser?.reload()?.await()

            if (currentUser != null) {
                _state.update {
                    it.copy(
                        userName = currentUser.displayName ?: "no name",
                        email = currentUser.email ?: "no mail",
                    )
                }
            }
        }
    }
}