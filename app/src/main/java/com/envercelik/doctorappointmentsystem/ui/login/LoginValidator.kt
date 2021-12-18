package com.envercelik.doctorappointmentsystem.ui.login

import java.util.regex.Pattern

class LoginValidator {
    private val emailValidationPattern: Pattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{4,49}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    fun validatePassword(password: String) = when {
        password.isEmpty() -> "password is required"
        else -> null
    }

    fun validateEmail(email: String) = when {
        email.isEmpty() -> "email is required"
        !emailValidationPattern.matcher(email).matches() -> "email is invalid"
        else -> null
    }
}