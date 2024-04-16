package com.endcodev.beautifullogin.data

import com.endcodev.beautifullogin.data.AuthMessage.Companion.ERROR_CREATING_ACCOUNT
import com.endcodev.beautifullogin.data.AuthMessage.Companion.ERROR_MAIL_IN_USE
import com.endcodev.beautifullogin.data.AuthMessage.Companion.ERROR_MAIL_OR_PASS
import com.endcodev.beautifullogin.data.AuthMessage.Companion.ERROR_SENDING_MAIL
import com.endcodev.beautifullogin.data.AuthMessage.Companion.MAIL_VERIFICATION_ERROR
import com.endcodev.beautifullogin.data.AuthMessage.Companion.OK
import com.endcodev.beautifullogin.domain.App.logV
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import javax.inject.Singleton

class AuthMessage {
    companion object {
        const val OK = 0
        const val MAIL_VERIFICATION_ERROR = 102
        const val ERROR_MAIL_OR_PASS = 103
        const val ERROR_SENDING_MAIL = 104
        const val ERROR_CREATING_ACCOUNT = 105
        const val ERROR_MAIL_IN_USE = 106
    }
}

@Singleton
class FirebaseAuth : KoinComponent {
    private val firebase: FirebaseClient by inject()

    fun createUser(email: String, pass: String, onCreateUserError: (Int) -> Unit) {
        val auth = firebase.auth

        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener { result ->
            if (result.isSuccessful) {
                sendMailVerification {
                    if (it == OK)
                        onCreateUserError(OK)
                    else
                        onCreateUserError(ERROR_SENDING_MAIL)
                }
            } else {
                if (result.exception is FirebaseAuthUserCollisionException)
                    onCreateUserError(ERROR_MAIL_IN_USE)
                else
                    onCreateUserError(ERROR_CREATING_ACCOUNT)
            }
        }
    }

    private fun sendMailVerification(onComplete: (Int) -> Unit) {
        firebase.auth.currentUser?.sendEmailVerification()?.addOnCompleteListener {
            if (it.isSuccessful)
                onComplete(OK)
            else
                onComplete(1)
        }
    }

    fun mailPassLogin(loginMail: String, loginPass: String, completionHandler: (Int) -> Unit) {
        firebase.auth.signInWithEmailAndPassword(loginMail, loginPass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (!Firebase.auth.currentUser?.isEmailVerified!!)
                        completionHandler(MAIL_VERIFICATION_ERROR)
                    else
                        completionHandler(OK)
                } else if (task.isCanceled || task.isComplete)
                    completionHandler(ERROR_MAIL_OR_PASS)
            }
    }

    fun disconnectUser() {
        firebase.auth.signOut()
    }

    fun deleteAccount() {
        Firebase.auth.currentUser?.delete()
    }

    fun changeEmail(newEmail: String) {
        logV("changeEmail")
        val user = Firebase.auth.currentUser
        logV("user ${user?.displayName} ")
        logV("new $newEmail ")

        user?.verifyBeforeUpdateEmail(newEmail)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Email update is successful
                logV("Email update successful")
            } else {
                // Email update failed
                logV("Email update failed")
            }
        }
    }

    fun changeUserName(newUser: String) {

        val user = Firebase.auth.currentUser

        val profileUpdates = userProfileChangeRequest {
            displayName = newUser
        }

        user?.updateProfile(profileUpdates)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    logV("User profile updated.")
                }
            }
    }
}