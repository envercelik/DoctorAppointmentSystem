package com.envercelik.doctorappointmentsystem

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.envercelik.doctorappointmentsystem.data.FirebaseAuthService
import com.envercelik.doctorappointmentsystem.data.FirebaseProfileService
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class SignupUserViewModel : ViewModel() {
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val nameSurname = MutableLiveData<String>()
    val birthDay = MutableLiveData<String>()
    val gender = MutableLiveData<Int>()

    private val _emailErrorMessage = MutableLiveData<String?>()
    val emailErrorMessage: MutableLiveData<String?> = _emailErrorMessage

    private val _passwordErrorMessage = MutableLiveData<String?>()
    val passwordErrorMessage: LiveData<String?> = _passwordErrorMessage

    private val _nameSurnameErrorMessage = MutableLiveData<String?>()
    val nameSurnameErrorMessage: LiveData<String?> = _nameSurnameErrorMessage

    private val _birthDayErrorMessage = MutableLiveData<String?>()
    val birthDayErrorMessage: LiveData<String?> = _birthDayErrorMessage

    private val _isGenderErrorMessageVisible = MutableLiveData<Boolean>(false)
    val isGenderErrorMessageVisible: LiveData<Boolean> = _isGenderErrorMessageVisible

    fun onSignupButtonClicked() {
        if (isEmailValid() and isPasswordValid() and isNameSurnameValid() and isBirthDayValid() and
            isGenderSelected()
        ) {
            val email = email.value.toString()
            val password = password.value.toString()

            signupUser(email,password)
        }
    }

    private fun signupUser(email: String, password: String) {
        viewModelScope.launch {
            when (val authResponse = FirebaseAuthService.signup(email, password)) {
                is Resource.Success -> saveUser(authResponse.data!!.uid)
                is Resource.Loading -> onLoading()
                is Resource.Error -> onError(authResponse.message)
            }
        }
    }

    private fun saveUser(uid: String) {
        viewModelScope.launch {
            when (val response =
                FirebaseProfileService.createUserInFireStore(getUserFromUi(), uid)) {
                is Resource.Success -> navigateToAppointmentScreen()
                is Resource.Loading -> onLoading()
                is Resource.Error -> onError(response.message)
            }
        }
    }

    private fun navigateToAppointmentScreen() {
        TODO("navigateToAppointmentScreen")
    }

    private fun onLoading() {
        TODO("show loading indicator")
    }

    private fun onError(message: String?) {
        TODO("show error message using toast or snack bar")

    }

    private fun getUserFromUi(): User {
        val nameSurname = nameSurname.value.toString()
        val gender = getGender()
        val birthYear = birthDay.value.toString()

        return User(nameSurname, gender, birthYear)
    }

    private fun getGender() = when (gender.value) {
        R.id.radioButtonMale -> "male"
        R.id.radioButtonFemale -> "female"
        else -> "unknown"
    }

    private fun isGenderSelected(): Boolean {
        _isGenderErrorMessageVisible.value = gender.value == 0
        return gender.value != 0
    }

    private fun isBirthDayValid(): Boolean {
        val birthDay = birthDay.value.orEmpty()
        when {
            birthDay.isEmpty() -> _birthDayErrorMessage.value = "birthday is required"
            birthDay.length != 4 -> _birthDayErrorMessage.value = "invalid birth day"
            else -> _birthDayErrorMessage.value = null
        }
        return _birthDayErrorMessage.value == null
    }

    private fun isNameSurnameValid(): Boolean {
        val nameSurname = nameSurname.value.orEmpty()
        when {
            nameSurname.isEmpty() -> _nameSurnameErrorMessage.value = "name surname is required"
            !nameSurname.all { it.isLetter() || it == ' ' } -> _nameSurnameErrorMessage.value =
                "invalid name surname"
            else -> _nameSurnameErrorMessage.value = null
        }
        return _nameSurnameErrorMessage.value == null
    }

    private fun isPasswordValid(): Boolean {
        val password = password.value.orEmpty()
        when {
            password.isEmpty() -> _passwordErrorMessage.value = "password is required"
            password.length < 7 -> _passwordErrorMessage.value = "password is too short"
            !password.any { it.isDigit() } -> _passwordErrorMessage.value =
                "password must contain one digit"
            password.all { it.isLetterOrDigit() } -> _passwordErrorMessage.value =
                "password must contain one special character"
            else -> _passwordErrorMessage.value = null
        }
        return _passwordErrorMessage.value == null
    }

    private fun isEmailValid(): Boolean {
        val email = email.value.orEmpty()
        val emailValidationPattern: Pattern = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{4,49}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
        )
        when {
            email.isEmpty() -> _emailErrorMessage.value = "email is required"
            !emailValidationPattern.matcher(email).matches() -> _emailErrorMessage.value =
                "email is invalid"
            else -> _emailErrorMessage.value = null
        }
        return _emailErrorMessage.value == null
    }
}