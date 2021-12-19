package com.envercelik.doctorappointmentsystem.ui.signup

import java.util.regex.Pattern

class SignupValidator {
    private val emailValidationPattern: Pattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{4,49}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    fun validateBirthdate(birthDay: String) = when {
        birthDay.isEmpty() -> "birthday is required"
        birthDay.length != 4 -> "invalid birth day"
        else -> null
    }

    fun validateNameSurname(nameSurname: String) = when {
        nameSurname.isEmpty() -> "name surname is required"
        !nameSurname.all { it.isLetter() || it == ' ' } -> "invalid name surname"
        else -> null
    }

    fun validatePassword(password: String) = when {
        password.isEmpty() -> "password is required"
        password.length < 7 -> "password is too short"
        !password.any { it.isDigit() } -> "password must contain one digit"
        password.all { it.isLetterOrDigit() } -> "password must contain one special character"
        else -> null
    }

    fun validateEmail(email: String) = when {
        email.isEmpty() -> "email is required"
        !emailValidationPattern.matcher(email).matches() -> "email is invalid"
        else -> null
    }

    fun validateHospital(hospitalName: String) = when {
        hospitalName.isEmpty() -> "hospital name is required"
        !hospitalName.all { it.isLetter() || it == ' ' } -> "invalid hospital name"
        else -> null
    }

    fun validateClinic(clinicName: String) = when {
        clinicName.isEmpty() -> "clinic name is required"
        !clinicName.all { it.isLetter() || it == ' ' } -> "invalid clinic name"
        else -> null
    }

    fun validateAddress(address: String) = when {
        address.isEmpty() -> "address is required"
        else -> null
    }
}