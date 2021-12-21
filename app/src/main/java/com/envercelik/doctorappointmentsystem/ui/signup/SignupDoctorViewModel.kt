package com.envercelik.doctorappointmentsystem.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.envercelik.doctorappointmentsystem.R
import com.envercelik.doctorappointmentsystem.Resource
import com.envercelik.doctorappointmentsystem.common.Constants.ROLE_DOCTOR
import com.envercelik.doctorappointmentsystem.data.FirebaseAuthService
import com.envercelik.doctorappointmentsystem.data.FirebaseProfileService
import com.envercelik.doctorappointmentsystem.ui.model.Doctor
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.launch

class SignupDoctorViewModel : ViewModel() {
    private val validator = SignupValidator()

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val nameSurname = MutableLiveData<String>()
    val birthDay = MutableLiveData<String>()
    val hospital = MutableLiveData<String>()
    val clinic = MutableLiveData<String>()
    val address = MutableLiveData<String>()
    val gender = MutableLiveData<Int>()

    private val _emailErrorMessage = MutableLiveData<String?>()
    val emailErrorMessage: MutableLiveData<String?> = _emailErrorMessage

    private val _passwordErrorMessage = MutableLiveData<String?>()
    val passwordErrorMessage: LiveData<String?> = _passwordErrorMessage

    private val _nameSurnameErrorMessage = MutableLiveData<String?>()
    val nameSurnameErrorMessage: LiveData<String?> = _nameSurnameErrorMessage

    private val _hospitalErrorMessage = MutableLiveData<String?>()
    val hospitalErrorMessage: LiveData<String?> = _hospitalErrorMessage

    private val _clinicErrorMessage = MutableLiveData<String?>()
    val clinicErrorMessage: LiveData<String?> = _clinicErrorMessage

    private val _addressErrorMessage = MutableLiveData<String?>()
    val addressErrorMessage: LiveData<String?> = _addressErrorMessage

    private val _birthDayErrorMessage = MutableLiveData<String?>()
    val birthDayErrorMessage: LiveData<String?> = _birthDayErrorMessage

    private val _isGenderErrorMessageVisible = MutableLiveData<Boolean>(false)
    val isGenderErrorMessageVisible: LiveData<Boolean> = _isGenderErrorMessageVisible


    private val _loadingBarState = MutableLiveData<Boolean>(false)
    val loadingBarState: LiveData<Boolean> = _loadingBarState

    private val _navigateToDoctorProfileScreenState = LiveEvent<Boolean>()
    val navigateToDoctorProfileScreenState: LiveEvent<Boolean> = _navigateToDoctorProfileScreenState

    private val _errorState = LiveEvent<String>()
    val errorState: LiveData<String> = _errorState

    fun onSignupButtonClicked() {
        if (isEmailValid() and isPasswordValid() and isNameSurnameValid() and isBirthDayValid() and
            isGenderSelected() and isHospitalValid() and isClinicValid() and isAddressValid()
        ) {
            val email = email.value.toString()
            val password = password.value.toString()

            signup(email, password)
        }
    }

    private fun signup(email: String, password: String) {
        viewModelScope.launch {
            when (val response = FirebaseAuthService.signup(email, password)) {
                is Resource.Success -> onSignupResponseSuccess(response.data!!.uid)
                is Resource.Loading -> onSignupResponseLoading()
                is Resource.Error -> onSignupResponseFail(response.message!!)
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
                is Resource.Success -> onSaveUserResponseSuccess()
                is Resource.Loading -> onSaveUserResponseLoading()
                is Resource.Error -> onSaveUserResponseFail(response.message!!)
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
        _navigateToDoctorProfileScreenState.value = true
    }

    private fun getUserFromUi(): Doctor {
        val nameSurname = nameSurname.value.toString()
        val gender = getGender()
        val birthYear = birthDay.value.toString()
        val hospital = hospital.value.toString()
        val clinic = clinic.value.toString()
        val address = address.value.toString()
        val role = ROLE_DOCTOR

        return Doctor(nameSurname, gender, birthYear, hospital, clinic, address, role)
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

    private fun isEmailValid(): Boolean {
        _emailErrorMessage.value = validator.validateEmail(email.value.orEmpty())
        return _emailErrorMessage.value == null
    }

    private fun isNameSurnameValid(): Boolean {
        _nameSurnameErrorMessage.value = validator.validateNameSurname(nameSurname.value.orEmpty())
        return _nameSurnameErrorMessage.value == null
    }

    private fun isPasswordValid(): Boolean {
        _passwordErrorMessage.value = validator.validatePassword(password.value.orEmpty())
        return _passwordErrorMessage.value == null
    }

    private fun isBirthDayValid(): Boolean {
        _birthDayErrorMessage.value = validator.validateBirthdate(birthDay.value.orEmpty())
        return _birthDayErrorMessage.value == null
    }

    private fun isHospitalValid(): Boolean {
        _hospitalErrorMessage.value = validator.validateHospital(hospital.value.orEmpty())
        return _hospitalErrorMessage.value == null
    }

    private fun isClinicValid(): Boolean {
        _clinicErrorMessage.value = validator.validateClinic(clinic.value.orEmpty())
        return _clinicErrorMessage.value == null
    }

    private fun isAddressValid(): Boolean {
        _addressErrorMessage.value = validator.validateAddress(address.value.orEmpty())
        return _addressErrorMessage.value == null
    }
}