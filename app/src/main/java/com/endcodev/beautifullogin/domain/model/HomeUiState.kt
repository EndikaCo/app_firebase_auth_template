package com.endcodev.beautifullogin.domain.model

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth

data class HomeUiState(
    val isLoading: Boolean = false,
    val image: Uri? = null,
    val email: String = "no mail",
    val userName: String = "no name",
    val editMode: Boolean = false,
)
