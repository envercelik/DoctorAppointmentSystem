package com.envercelik.doctorappointmentsystem.ui.model

data class Patient(
    override var uuid: String,
    override var nameSurname: String,
    override var birthYear: String,
    override var gender: String,
    override var role: String
) : User()