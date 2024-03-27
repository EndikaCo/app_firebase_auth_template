package com.endcodev.beautifullogin.presentation

import androidx.lifecycle.ViewModel
import com.endcodev.beautifullogin.data.FirebaseClient
import com.endcodev.beautifullogin.domain.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomeViewModel : ViewModel(), KoinComponent {
    private val client: FirebaseClient by inject()

    private val _state = MutableStateFlow(HomeUiState())
    val state = _state.asStateFlow()

    init {
        _state.update { it.copy(auth = client.auth) }
    }
}