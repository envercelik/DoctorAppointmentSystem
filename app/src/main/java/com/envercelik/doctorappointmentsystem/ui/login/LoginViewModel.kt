package com.envercelik.doctorappointmentsystem.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    private val validator = LoginValidator()

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private val _emailErrorMessage = MutableLiveData<String?>()
    val emailErrorMessage: MutableLiveData<String?> = _emailErrorMessage

    private val _passwordErrorMessage = MutableLiveData<String?>()
    val passwordErrorMessage: LiveData<String?> = _passwordErrorMessage

    fun onLoginButtonClicked() {
        if (isEmailValid() and isPasswordValid()) {
            val email = email.value.toString()
            val password = password.value.toString()
            println(email)
            println(password)
            //send login request
        }
    }

    private fun isEmailValid(): Boolean {
        _emailErrorMessage.value = validator.validateEmail(email.value.orEmpty())
        return _emailErrorMessage.value == null
    }

    private fun isPasswordValid(): Boolean {
        _passwordErrorMessage.value = validator.validatePassword(password.value.orEmpty())
        return _passwordErrorMessage.value == null
    }
}