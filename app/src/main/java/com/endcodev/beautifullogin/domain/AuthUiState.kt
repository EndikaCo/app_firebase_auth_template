package com.endcodev.beautifullogin.domain

data class AuthUiState(
    val email: String = "",
    val password: String = "",
    val userName: String = "",
    val isAuthButtonEnabled: Boolean = false,
    val isLoading: Boolean = false,
    val emailError: String? = null,
    val passwordError: String? = null,
    val userNameError: String? = null
)
