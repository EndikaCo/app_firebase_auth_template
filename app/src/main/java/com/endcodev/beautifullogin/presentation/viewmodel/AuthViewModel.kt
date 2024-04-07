package com.endcodev.beautifullogin.presentation.viewmodel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.util.Patterns
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.endcodev.beautifullogin.R
import com.endcodev.beautifullogin.data.FirebaseAuth
import com.endcodev.beautifullogin.data.FirebaseClient
import com.endcodev.beautifullogin.domain.App
import com.endcodev.beautifullogin.domain.AuthUiState
import com.endcodev.beautifullogin.domain.DialogErrorUiState
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
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
    val uiState = _uiState.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _uiState.value)

    private val _dialogState = MutableStateFlow(DialogErrorUiState())
    val dialogState = _dialogState.asStateFlow()

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn = _isLoggedIn.asStateFlow()

    init {
        enableLogin(uiState.value.email, uiState.value.password)
        updateLoginState()
    }

    private fun updateLoginState() {
        val mClient = client.auth.currentUser
        _isLoggedIn.value = mClient?.isEmailVerified == true
    }

    fun disconnectUser() {
        auth.disconnectUser()
    }

    fun gLoginInit(result: ActivityResult?) {

        if (result != null) {

            if (result.resultCode == Activity.RESULT_OK) {

                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)

                try {
                    val account = task.getResult(ApiException::class.java)
                    if (account != null)
                        gLogin(account)
                } catch (e: ApiException) {
                    Log.e(App.tag, "gLoginInit: error", )
                }
            }
        }
        //binding.progress.visibility = View.GONE
    }

    private fun gLogin(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        val auth = com.google.firebase.auth.FirebaseAuth.getInstance()
        auth.signInWithCredential(credential)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.v(App.tag, "gLogin: Login success", )
                } else {
                    Log.v(App.tag, "gLogin: Login fail", )
                }
                updateLoginState()
            }
    }

    fun googleLogin(
        context: Context,
        launcher: ManagedActivityResultLauncher<Intent, ActivityResult>
    ) {
        val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleClient = GoogleSignIn.getClient(context, googleConf)
        val signInIntent = Intent(googleClient.signInIntent)
        launcher.launch(signInIntent)
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
            updateLoginState() //todo
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