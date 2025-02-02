package com.example.gestionentidades

import java.time.LocalDate

data class Reno(
    val id: Int,
    val nombre: String,
    var edad: Int,
    var peso: Double,
    var esLider: Boolean,
    val fechaIncorporacion: LocalDate
)
