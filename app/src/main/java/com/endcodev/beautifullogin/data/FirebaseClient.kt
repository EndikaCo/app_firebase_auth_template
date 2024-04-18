package com.endcodev.beautifullogin.data

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Singleton

@Singleton
class FirebaseClient {
    val auth: FirebaseAuth get() = FirebaseAuth.getInstance()
}

