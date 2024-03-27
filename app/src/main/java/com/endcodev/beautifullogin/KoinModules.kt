package com.endcodev.beautifullogin

import com.endcodev.beautifullogin.data.FirebaseAuth
import com.endcodev.beautifullogin.data.FirebaseClient
import com.endcodev.beautifullogin.presentation.AuthViewModel
import org.koin.dsl.module

val authModule = module {
    single { FirebaseAuth() }
    single { FirebaseClient() }
}

val appModule = module {
    factory { AuthViewModel() }
}