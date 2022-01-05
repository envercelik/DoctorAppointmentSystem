package com.envercelik.doctorappointmentsystem.ui.doctordetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class DoctorDetailViewModelFactory(
    private val doctorUid: String,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DoctorDetailViewModel(doctorUid) as T
    }
}