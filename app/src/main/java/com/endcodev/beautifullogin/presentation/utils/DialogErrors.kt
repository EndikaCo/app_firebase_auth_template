package com.endcodev.beautifullogin.presentation.utils

import android.app.AlertDialog
import android.content.Context
import com.endcodev.beautifullogin.data.AuthMessage
import com.endcodev.beautifullogin.domain.model.DialogErrorUiState

fun getError(errorNum: Int): DialogErrorUiState {

    val title = "Error $errorNum"
    val description: String = when (errorNum) {
        AuthMessage.MailVerificationError.error -> "Mail Verification Error"
        AuthMessage.ErrorMailOrPass.error -> "Error Mail Or Pass"
        AuthMessage.ErrorSendingMail.error -> "Error Sending Mail"
        AuthMessage.ErrorCreatingAccount.error -> "Error Creating Account"
        AuthMessage.ErrorMailInUse.error -> "Error Mail already in use"
        else -> {
            "Undefined Error"
        }
    }
    return DialogErrorUiState(true, title, description, "Ok")
}

fun showDialog(context: Context, title: String, message: String) {
    val builder = AlertDialog.Builder(context)
    builder.setTitle(title)
    builder.setMessage(message)
    builder.setPositiveButton("OK") { dialog, _ ->
        dialog.dismiss()
    }
    val alertDialog = builder.create()
    alertDialog.show()
}