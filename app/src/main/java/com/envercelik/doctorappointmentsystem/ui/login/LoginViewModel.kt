package com.envercelik.doctorappointmentsystem.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.envercelik.doctorappointmentsystem.Resource.*
import com.envercelik.doctorappointmentsystem.data.FirebaseAuthService
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val validator = LoginValidator()

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private val _emailErrorMessage = MutableLiveData<String?>()
    val emailErrorMessage: MutableLiveData<String?> = _emailErrorMessage

    private val _passwordErrorMessage = MutableLiveData<String?>()
    val passwordErrorMessage: LiveData<String?> = _passwordErrorMessage

    private val _loadingBarState = MutableLiveData<Boolean>(false)
    val loadingBarState: LiveData<Boolean> = _loadingBarState

    private val _navigateToAppointmentScreenState = LiveEvent<Boolean>()
    val navigateToAppointmentScreenState: LiveEvent<Boolean> = _navigateToAppointmentScreenState

    private val _navigateToUserSignupScreenState = LiveEvent<Boolean>()
    val navigateToUserSignupScreenState: LiveEvent<Boolean> = _navigateToUserSignupScreenState

    private val _navigateToDoctorSignupScreenState = LiveEvent<Boolean>()
    val navigateToDoctorSignupScreenState: LiveEvent<Boolean> = _navigateToDoctorSignupScreenState

    private val _errorState = LiveEvent<String>()
    val errorState: LiveData<String> = _errorState

    fun onLoginButtonClicked() {
        if (isEmailValid() and isPasswordValid()) {
            val email = email.value.toString()
            val password = password.value.toString()

            login(email, password)
        }
    }

    fun onCreateUserAccountButtonClicked() {
        _navigateToUserSignupScreenState.value = true
    }

    fun onCreateDoctorAccountButtonClicked() {
        _navigateToDoctorSignupScreenState.value = true
    }

    private fun login(email: String, password: String) {
        viewModelScope.launch {
            when (val response = FirebaseAuthService.login(email, password)) {
                is Success -> onLoginResponseSuccess()
                is Loading -> onLoginResponseLoading()
                is Error -> onSignupResponseFail(response.message!!)
            }
        }
    }

    private fun onLoginResponseSuccess() {
        _loadingBarState.value = false
        _navigateToAppointmentScreenState.value = true
    }

    private fun onLoginResponseLoading() {
        _loadingBarState.value = true
    }

    private fun onSignupResponseFail(message: String) {
        _loadingBarState.value = false
        _errorState.value = message
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