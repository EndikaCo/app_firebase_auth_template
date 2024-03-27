package com.endcodev.beautifullogin.presentation

import android.os.Handler
import android.os.Looper
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.endcodev.beautifullogin.data.FirebaseAuth
import com.endcodev.beautifullogin.data.FirebaseClient
import com.endcodev.beautifullogin.domain.AuthUiState
import com.endcodev.beautifullogin.domain.DialogErrorUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AuthViewModel : ViewModel(), KoinComponent {

    companion object {
        const val MIN_PASS_LENGTH = 6
        const val MIN_NAME_LENGTH = 3
    }

    private val auth: FirebaseAuth by inject()
    private val client: FirebaseClient by inject()

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState =
        _uiState.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _uiState.value)

    private val _dialogState = MutableStateFlow(DialogErrorUiState())
    val dialogState = _dialogState.asStateFlow()

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn = _isLoggedIn.asStateFlow()

    fun checkLoginState() {
        //client.auth.currentUser?.reload()
        val mClient = client.auth.currentUser
        if (mClient?.isEmailVerified == true)
            _isLoggedIn.value = true
    }

    fun showDialog(title: String, message: String, positiveButton: String) {
        _dialogState.update {
            it.copy(
                show = true,
                title = title,
                message = message,
                positiveButton = positiveButton
            )
        }
    }
    fun hideDialog() { _dialogState.update { DialogErrorUiState() } }

    fun mailPassLogin(onResult: (Int) -> Unit) {
        auth.mailPassLogin(uiState.value.email, uiState.value.password) { error ->
            onResult(error)
        }
    }

    fun createUser(error: (Int) -> Unit) {
        auth.createUser(uiState.value.email, uiState.value.password) {error(it) }
    }

    fun updateReset(email: String) {
        _uiState.update {
            it.copy(
                email = email,
                isAuthButtonEnabled = Patterns.EMAIL_ADDRESS.matcher(email).matches()
            )
        }
    }

    fun updateLogin(email: String, pass: String) {
        _uiState.update {
            it.copy(
                email = email,
                password = pass,
                isAuthButtonEnabled = enableLogin(email, pass)
            )
        }
    }

    fun updateSignUp(
        email: String? = null,
        pass: String? = null,
        userName: String? = null
    ) {
        val mEmail = email ?: uiState.value.email
        val mEmailError =
            if (mEmail.isBlank()) null else if (isMailValid(mEmail)) null else "Invalid email"
        val mPass = pass ?: uiState.value.password
        val mPassError =
            if (mPass.isBlank()) null else if (isPassValid(mPass)) null else "Password < 6 character"
        val mUserName = userName ?: uiState.value.userName
        val mUserNameError =
            if (mUserName.isBlank()) null else if (isNameValid(mUserName)) null else "Name < 3 character"

        _uiState.update {
            it.copy(
                email = mEmail,
                emailError = mEmailError,
                password = mPass,
                passwordError = mPassError,

                userName = mUserName,
                userNameError = mUserNameError,
                isAuthButtonEnabled = enableSignUp(mEmail, mPass, mUserName)
            )
        }
    }

    fun listenerMailVerification(onSuccess: () -> Unit) {
        val handler = Handler(Looper.getMainLooper())
        val runnableCode: Runnable = object : Runnable {
            override fun run() {

                client.auth.currentUser?.reload()
                val mClient = client.auth.currentUser

                if (mClient?.isEmailVerified == false) { // Check user's email verified
                    handler.postDelayed(this, 2000) // Repeat block every 2s
                } else {
                    onSuccess()
                }
            }
        }
        handler.post(runnableCode)
    }

    private fun enableSignUp(
        email: String,
        password: String,
        userName: String,
    ): Boolean = isMailValid(email)
            && isPassValid(password)
            && isNameValid(userName)

    private fun enableLogin(email: String, password: String) =
        isMailValid(email) && isPassValid(password)

    private fun isMailValid(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    private fun isPassValid(pass: String) = pass.length > MIN_PASS_LENGTH
    private fun isNameValid(name: String) = name.length > MIN_NAME_LENGTH


}