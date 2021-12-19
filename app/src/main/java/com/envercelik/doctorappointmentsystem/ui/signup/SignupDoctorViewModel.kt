package com.envercelik.doctorappointmentsystem.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

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

    fun onSignupButtonClicked() {
        if (isEmailValid() and isPasswordValid() and isNameSurnameValid() and isBirthDayValid() and
            isGenderSelected() and isHospitalValid() and isClinicValid() and isAddressValid()
        ) {
            val email = email.value.toString()
            val password = password.value.toString()

            //send signup request
        }
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