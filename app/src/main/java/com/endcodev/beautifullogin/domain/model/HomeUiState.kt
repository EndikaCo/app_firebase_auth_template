package com.endcodev.beautifullogin.domain.model

import android.net.Uri

data class HomeUiState(
    val isLoading: Boolean = false,
    val image: Uri? = null,
    val email: String = "no mail",
    val userName: String = "no name",
    val editMode: Boolean = false,
)
