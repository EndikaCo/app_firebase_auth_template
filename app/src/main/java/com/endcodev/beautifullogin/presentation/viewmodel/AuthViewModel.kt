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
import com.endcodev.beautifullogin.data.AuthMessage
import com.endcodev.beautifullogin.data.AuthMessage.Companion.ERROR_MAIL_OR_PASS
import com.endcodev.beautifullogin.data.AuthMessage.Companion.MAIL_VERIFICATION_ERROR
import com.endcodev.beautifullogin.data.AuthMessage.Companion.OK
import com.endcodev.beautifullogin.data.FirebaseAuth
import com.endcodev.beautifullogin.data.FirebaseClient
import com.endcodev.beautifullogin.domain.App
import com.endcodev.beautifullogin.domain.model.AuthUiState
import com.endcodev.beautifullogin.presentation.utils.UiText
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn = _isLoggedIn.asStateFlow()

    private val _errorChannel = Channel<UiText>()
    val errorChannel = _errorChannel.receiveAsFlow()

    private fun triggerAlert(error: UiText.StringResource) {
        viewModelScope.launch {
            _errorChannel.send(error)
        }
    }

    init {
        enableLogin(uiState.value.email, uiState.value.password)
        updateLoginState()
    }

    private fun updateLoginState() {
        val mClient = client.auth.currentUser
        _isLoggedIn.value = mClient?.isEmailVerified == true
    }

    fun gLoginInit(result: ActivityResult?) {

        _uiState.update { it.copy(isLoading = true) }

        if (result != null) {

            if (result.resultCode == Activity.RESULT_OK) {

                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)

                try {
                    val account = task.getResult(ApiException::class.java)
                    if (account != null)
                        gLogin(account)
                } catch (e: ApiException) {
                    Log.e(App.tag, "gLoginInit: error")
                }
            }
        }
    }

    private fun gLogin(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        val auth = com.google.firebase.auth.FirebaseAuth.getInstance()
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful)
                    triggerAlert(UiText.StringResource(resId = R.string.login_success))
                else
                    triggerAlert(UiText.StringResource(resId = R.string.login_fail))

                _uiState.update { it.copy(isLoading = false) }
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

    fun mailPassLogin() {
        auth.mailPassLogin(
            loginMail = uiState.value.email,
            loginPass = uiState.value.password,
            completionHandler = { error ->
                val message = when (error) {
                    OK -> R.string.login_success
                    MAIL_VERIFICATION_ERROR -> R.string.login_error_verification
                    ERROR_MAIL_OR_PASS -> R.string.login_error_mail_pass
                    else -> R.string.error
                }
                triggerAlert(UiText.StringResource(resId = message))
                updateLoginState()
            }
        )
    }

    fun createUser(error: (Int) -> Unit) {
        auth.createUser(
            email = uiState.value.email,
            pass = uiState.value.password,
            onCreateUserError = { error(it) }
        )
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
        userName: String? = null,
        terms: Boolean? = null
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
        val mTerms = terms ?: uiState.value.isTermsChecked

        _uiState.update {
            it.copy(
                email = mEmail,
                emailError = mEmailError,
                password = mPass,
                passwordError = mPassError,
                userName = mUserName,
                userNameError = mUserNameError,
                isAuthButtonEnabled = enableSignUp(mEmail, mPass, mUserName, mTerms),
                isTermsChecked = mTerms
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
        terms: Boolean
    ): Boolean = isMailValid(email)
            && isPassValid(password)
            && isNameValid(userName)
            && terms

    private fun enableLogin(email: String, password: String) =
        isMailValid(email) && isPassValid(password)

    private fun isMailValid(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    private fun isPassValid(pass: String) = pass.length > MIN_PASS_LENGTH
    private fun isNameValid(name: String) = name.length > MIN_NAME_LENGTH
}