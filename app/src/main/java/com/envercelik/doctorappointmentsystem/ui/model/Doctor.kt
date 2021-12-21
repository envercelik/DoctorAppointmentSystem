package com.envercelik.doctorappointmentsystem.ui.model

data class Doctor(
    override var nameSurname: String,
    override var gender: String,
    override var birthYear: String,
    override var role: String,
    val hospital: String,
    val clinic: String,
    val address: String,
) : User()