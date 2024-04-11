package com.endcodev.beautifullogin.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.endcodev.beautifullogin.data.FirebaseAuth
import com.endcodev.beautifullogin.data.FirebaseClient
import com.endcodev.beautifullogin.domain.model.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomeViewModel : ViewModel(), KoinComponent {
    private val client: FirebaseClient by inject()
    private val auth: FirebaseAuth by inject()
    private val _state = MutableStateFlow(HomeUiState())
    val state = _state.asStateFlow()

    init {
        val client = client.auth.currentUser
        _state.update {
            it.copy(
                email = client?.email ?: "no mail",
                userName = client?.displayName ?: "no name",
                image = client?.photoUrl
            )
        }
    }

    fun changeMail(mail: String) {
        _state.update { it.copy(email = mail) }
    }

    fun changeUserName(name: String) {
        //todo
        _state.update { it.copy(userName = name) }
    }

    fun disconnectUser() {
        auth.disconnectUser()
    }

    fun deleteAccount() {
        auth.deleteAccount()
    }

    fun saveNewInfo(){
        auth.changeUserName(_state.value.userName)
    }
}