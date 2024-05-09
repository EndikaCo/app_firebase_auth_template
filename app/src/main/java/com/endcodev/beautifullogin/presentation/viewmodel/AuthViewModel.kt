package com.endcodev.beautifullogin.presentation.viewmodel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Patterns
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.endcodev.beautifullogin.R
import com.endcodev.beautifullogin.data.AuthMessage.Companion.ERROR_CREATING_ACCOUNT
import com.endcodev.beautifullogin.data.AuthMessage.Companion.ERROR_MAIL_IN_USE
import com.endcodev.beautifullogin.data.AuthMessage.Companion.ERROR_MAIL_OR_PASS
import com.endcodev.beautifullogin.data.AuthMessage.Companion.ERROR_SENDING_MAIL
import com.endcodev.beautifullogin.data.AuthMessage.Companion.MAIL_VERIFICATION_ERROR
import com.endcodev.beautifullogin.data.AuthMessage.Companion.OK
import com.endcodev.beautifullogin.data.FirebaseAuth
import com.endcodev.beautifullogin.data.FirebaseClient
import com.endcodev.beautifullogin.domain.model.AuthUiState
import com.endcodev.beautifullogin.presentation.utils.UiText
import com.endcodev.beautifullogin.presentation.utils.ValidationUtil.enableLogin
import com.endcodev.beautifullogin.presentation.utils.ValidationUtil.enableSignUp
import com.endcodev.beautifullogin.presentation.utils.ValidationUtil.isMailValid
import com.endcodev.beautifullogin.presentation.utils.ValidationUtil.isNameValid
import com.endcodev.beautifullogin.presentation.utils.ValidationUtil.isPassValid
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
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

    private val authStateListener =
        com.google.firebase.auth.FirebaseAuth.AuthStateListener { firebaseAuth ->
            _isLoggedIn.value = firebaseAuth.currentUser?.isEmailVerified == true
        }

    init {
        client.auth.addAuthStateListener(authStateListener)
    }

    fun gLoginInit(result: ActivityResult?) {

        _uiState.update { it.copy(isLoading = true) }

        if (result != null && result.resultCode == Activity.RESULT_OK) {

            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)

            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    client.auth.signInWithCredential(credential)
                        .addOnCompleteListener { it ->
                            if (it.isSuccessful)
                                triggerAlert(UiText.StringResource(resId = R.string.login_success))
                            else
                                triggerAlert(UiText.StringResource(resId = R.string.login_fail))

                            _uiState.update { it.copy(isLoading = false) }
                        }
                }
            } catch (e: ApiException) {
                triggerAlert(UiText.StringResource(resId = R.string.google_login_fail))
                _uiState.update { it.copy(isLoading = false) }
            }
        } else
            _uiState.update { it.copy(isLoading = false) }
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

        _uiState.update { it.copy(isLoading = true) }

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

                viewModelScope.launch {
                    delay(1000)
                    triggerAlert(UiText.StringResource(resId = message))
                    _uiState.update { it.copy(isLoading = false) }
                }
            }
        )
    }

    fun createUser(error: (Int) -> Unit) {
        _uiState.update { it.copy(isLoading = true) }

        auth.createUser(
            email = uiState.value.email,
            pass = uiState.value.password,
            onCreateUserError = { ret ->
                when (ret) {
                    OK -> error(ret)
                    ERROR_SENDING_MAIL -> triggerAlert(UiText.StringResource(resId = R.string.error_sending_mail))
                    ERROR_MAIL_IN_USE -> triggerAlert(UiText.StringResource(resId = R.string.error_mail_in_use))
                    ERROR_CREATING_ACCOUNT -> triggerAlert(UiText.StringResource(resId = R.string.error_creating_account))
                }
                _uiState.update { it.copy(isLoading = false) }
            }
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

    override fun onCleared() {
        super.onCleared()
        client.auth.removeAuthStateListener(authStateListener)
    }

    fun resetPassword(mail: String, listenerUnit: () -> Unit) {
        _uiState.update { it.copy(isLoading = true) }

        auth.resetPassword(email = mail, onComplete = { error ->

            viewModelScope.launch {

                if (error == OK) {
                    triggerAlert(UiText.StringResource(resId = R.string.reset_password))
                    delay(1000)
                    listenerUnit()
                    _uiState.update { it.copy(isLoading = false) }
                } else {
                    triggerAlert(UiText.StringResource(resId = R.string.reset_error))
                    delay(1000)
                    listenerUnit()
                    _uiState.update { it.copy(isLoading = false) }
                }
            }
        })
    }
}