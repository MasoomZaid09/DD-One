package org.dd_healthcare.internal_logcat.utils

object Validator {

    fun isValidEmail(email: String): Boolean {
        return email.contains("@") && email.contains(".")
    }

    fun isValidPassword(password: String): Boolean {
        return password.length >= 4
    }
}