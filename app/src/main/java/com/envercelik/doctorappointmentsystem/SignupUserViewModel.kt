package com.envercelik.doctorappointmentsystem

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignupUserViewModel : ViewModel() {
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val nameSurname = MutableLiveData<String>()
    val birthDay = MutableLiveData<String>()
    val gender = MutableLiveData<Int>()

    fun onSignupButtonClicked() {
        println(email.value.toString())
        println(password.value.toString())
        println(nameSurname.value.toString())
        println(birthDay.value.toString())
        println(gender.value.toString())
    }
}