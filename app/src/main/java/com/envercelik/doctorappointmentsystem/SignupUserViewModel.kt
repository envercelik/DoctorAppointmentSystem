package com.envercelik.doctorappointmentsystem

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.envercelik.doctorappointmentsystem.Resource.Error
import com.envercelik.doctorappointmentsystem.Resource.Loading
import com.envercelik.doctorappointmentsystem.Resource.Success
import com.envercelik.doctorappointmentsystem.data.FirebaseAuthService
import com.envercelik.doctorappointmentsystem.data.FirebaseProfileService
import com.hadilq.liveevent.LiveEvent
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

    private val _loadingBarState = MutableLiveData<Boolean>(false)
    val loadingBarState: LiveData<Boolean> = _loadingBarState

    private val _navigateToAppointmentScreenState = LiveEvent<Boolean>()
    val navigateToAppointmentScreenState: LiveEvent<Boolean> = _navigateToAppointmentScreenState

    private val _errorState = LiveEvent<String>()
    val errorState: LiveData<String> = _errorState

    fun onSignupButtonClicked() {
        if (isEmailValid() and isPasswordValid() and isNameSurnameValid() and isBirthDayValid() and
            isGenderSelected()
        ) {
            val email = email.value.toString()
            val password = password.value.toString()

            signup(email,password)
        }
    }

    private fun signup(email: String, password: String) {
        viewModelScope.launch {
            when (val response = FirebaseAuthService.signup(email, password)) {
                is Success -> onSignupResponseSuccess(response.data!!.uid)
                is Loading -> onSignupResponseLoading()
                is Error -> onSignupResponseFail(response.message!!)
            }
        }
    }

    private fun onSignupResponseFail(message: String) {
        _loadingBarState.value = false
        _errorState.value = message
    }

    private fun onSignupResponseLoading() {
        _loadingBarState.value = true
    }

    private fun onSignupResponseSuccess(uid: String) {
        saveUser(uid)
    }

    private fun saveUser(uid: String) {
        viewModelScope.launch {
            when (val response =
                FirebaseProfileService.createUserInFireStore(getUserFromUi(), uid)) {
                is Success -> onSaveUserResponseSuccess()
                is Loading -> onSaveUserResponseLoading()
                is Error -> onSaveUserResponseFail(response.message!!)
            }
        }
    }

    private fun onSaveUserResponseFail(message: String) {
        _loadingBarState.value = false
        _errorState.value = message
        //deleteUser()
    }

    private fun onSaveUserResponseLoading() {
        _loadingBarState.value = true
    }

    private fun onSaveUserResponseSuccess() {
        _loadingBarState.value = false
        _navigateToAppointmentScreenState.value = true
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