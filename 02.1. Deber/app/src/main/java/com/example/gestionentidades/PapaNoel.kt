package com.example.gestionentidades

import java.time.LocalDate

data class PapaNoel(
    val nombre: String,
    var edad: Int,
    var peso: Double,
    val paisResidencia: String,
    val fechaInicio: LocalDate,
    val renos: ArrayList<Reno> = arrayListOf()
)