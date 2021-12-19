package com.envercelik.doctorappointmentsystem.ui.model

data class User(
    val nameSurname: String,
    val gender: String,
    val birthYear: String,
    val hospital: String = "",
    val clinic: String = "",
    val address: String = "",
    val role: String = ""
)