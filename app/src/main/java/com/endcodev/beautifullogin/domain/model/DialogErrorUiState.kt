package com.endcodev.beautifullogin.domain.model

data class DialogErrorUiState(
    val show: Boolean = false,
    val title: String = "",
    val message: String = "",
    val positiveButton: String = "Ok",
)
