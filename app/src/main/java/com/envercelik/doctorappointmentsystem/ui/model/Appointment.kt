package com.envercelik.doctorappointmentsystem.ui.model

data class Appointment(
    val doctorUid: String,
    val patientUid: String = "",
    val hour: String,
    val isAvailable: Boolean = true
) {
    val id = doctorUid + hour
}