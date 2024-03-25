package com.endcodev.beautifullogin.domain

data class AuthUiState(
    val email: String = "endikacorreia@gmail.com", //todo
    val password: String = "qwertyuiop",
    val userName: String = "",
    val isAuthButtonEnabled: Boolean = false,
    val isLoading: Boolean = false,
    val emailError: String? = null,
    val passwordError: String? = null,
    val userNameError: String? = null
)
