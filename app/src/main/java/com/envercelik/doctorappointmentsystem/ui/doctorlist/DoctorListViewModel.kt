package com.envercelik.doctorappointmentsystem.ui.doctorlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.envercelik.doctorappointmentsystem.Resource
import com.envercelik.doctorappointmentsystem.data.FirebaseProfileService
import com.envercelik.doctorappointmentsystem.ui.model.Doctor
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DoctorListViewModel : ViewModel() {

    private val _doctors = MutableLiveData<List<Doctor>>()
    val doctors: LiveData<List<Doctor>> = _doctors

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    init {
        fetchDoctors()
    }

    private fun fetchDoctors() {
        viewModelScope.launch {
            FirebaseProfileService.getAllDoctorsFromFirestore().collect {
                when (it) {
                    is Resource.Success -> onFetchDoctorsResponseSuccess(it.data!!)
                    is Resource.Loading -> onFetchDoctorsResponseLoading()
                    is Resource.Error -> onFetchDoctorsResponseFail(it.message!!)
                }
            }
        }
    }

    private fun onFetchDoctorsResponseSuccess(doctors: List<Doctor>) {
        _loading.value = false
        _doctors.postValue(doctors)
    }

    private fun onFetchDoctorsResponseLoading() {
        _loading.value = true
    }

    private fun onFetchDoctorsResponseFail(message: String) {
        _loading.value = false
        _error.value = message
    }
}