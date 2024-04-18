package com.endcodev.beautifullogin.presentation.utils

import java.util.regex.Pattern

object ValidationUtil {

    private const val MIN_PASS_LENGTH = 6
    private const val MIN_NAME_LENGTH = 3

    fun enableSignUp(email: String, password: String, userName: String, terms: Boolean): Boolean =
        isMailValid(email) && isPassValid(password) && isNameValid(userName) && terms

    fun enableLogin(email: String, password: String): Boolean =
        isMailValid(email) && isPassValid(password)

    fun isMailValid(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$"
        val pattern = Pattern.compile(emailRegex)
        return pattern.matcher(email).matches()
    }

    fun isPassValid(pass: String): Boolean = pass.length > MIN_PASS_LENGTH

    fun isNameValid(name: String): Boolean = name.length > MIN_NAME_LENGTH
}