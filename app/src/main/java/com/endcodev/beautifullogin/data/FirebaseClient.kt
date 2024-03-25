package com.endcodev.beautifullogin.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import javax.inject.Singleton

@Singleton
class FirebaseClient {
    val auth: FirebaseAuth get() = FirebaseAuth.getInstance()
    val db = Firebase.database

    fun checkLoginState(): Boolean {
        auth.currentUser?.reload()
        val mClient = auth.currentUser
        return mClient?.isEmailVerified == true
    }
}

