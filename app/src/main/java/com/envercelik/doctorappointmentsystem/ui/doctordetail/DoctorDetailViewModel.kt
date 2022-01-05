package com.envercelik.doctorappointmentsystem.ui.doctordetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.envercelik.doctorappointmentsystem.Resource.*
import com.envercelik.doctorappointmentsystem.data.FirebaseProfileService
import com.envercelik.doctorappointmentsystem.ui.model.Doctor
import kotlinx.coroutines.launch

class DoctorDetailViewModel(
    private val uid: String
) : ViewModel() {

    private val _doctor = MutableLiveData<Doctor>()
    val doctor: LiveData<Doctor> = _doctor

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    init {
        fetchDoctorById(uid)
    }

    private fun fetchDoctorById(uid: String) {
        viewModelScope.launch {
            FirebaseProfileService.getDoctorFromFirestoreById(uid).collect {
                when (it) {
                    is Success -> onFetchDoctorResponseSuccess(it.data!!)
                    is Loading -> onFetchDoctorResponseLoading()
                    is Error -> onFetchDoctorResponseFail(it.message!!)
                }
            }
        }
    }

    private fun onFetchDoctorResponseSuccess(doctor: Doctor) {
        _loading.value = false
        _doctor.postValue(doctor)
    }

    private fun onFetchDoctorResponseLoading() {
        _loading.value = true
    }

    private fun onFetchDoctorResponseFail(message: String) {
        println(message)
        _loading.value = false
        _error.value = message
    }
}