package com.endcodev.beautifullogin.data

import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import javax.inject.Singleton

sealed class AuthMessage(val error: Int) {
    data object OK : AuthMessage(error = 0)
    data object MailVerificationError : AuthMessage(error = 102)
    data object ErrorMailOrPass : AuthMessage(error = 103)
    data object ErrorSendingMail : AuthMessage(error = 104) // Unable to send verification email
    data object ErrorCreatingAccount : AuthMessage(error = 105) // Unable to send verification email
    data object ErrorMailInUse : AuthMessage(error = 106) // Email already in use
}

@Singleton
class FirebaseAuth : KoinComponent {
    private val firebase: FirebaseClient by inject()

    fun createUser(email: String, pass: String, onCreateUserError: (Int) -> Unit) {
        val auth = firebase.auth

        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener { result ->
            if (result.isSuccessful) {
                sendMailVerification {
                    if (it == AuthMessage.OK.error) {
                        onCreateUserError(AuthMessage.OK.error)
                    } else {
                        onCreateUserError(AuthMessage.ErrorSendingMail.error)
                    }
                }
            } else {
                if (result.exception is FirebaseAuthUserCollisionException) {
                    onCreateUserError(AuthMessage.ErrorMailInUse.error)
                } else {
                    onCreateUserError(AuthMessage.ErrorCreatingAccount.error)
                }
            }
        }
    }

    private fun sendMailVerification(onComplete: (Int) -> Unit) {
        Firebase.auth.currentUser?.sendEmailVerification()?.addOnCompleteListener {
            if (it.isSuccessful) {
                onComplete(AuthMessage.OK.error)
            } else {
                onComplete(1)
            }
        }
    }

    fun mailPassLogin(loginMail: String, loginPass: String, completionHandler: (Int) -> Unit) {
        Firebase.auth.signInWithEmailAndPassword(loginMail, loginPass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (!Firebase.auth.currentUser?.isEmailVerified!!) {
                        completionHandler(AuthMessage.MailVerificationError.error)
                    } else {
                        completionHandler(AuthMessage.OK.error)
                    }
                } else if (task.isCanceled || task.isComplete) {
                    completionHandler(AuthMessage.ErrorMailOrPass.error)
                }
            }
    }

    fun disconnectUser() {
        Firebase.auth.signOut()
    }
}