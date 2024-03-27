package com.endcodev.beautifullogin.domain

import com.google.firebase.auth.FirebaseAuth

data class HomeUiState (
    val auth: FirebaseAuth? = null
)
