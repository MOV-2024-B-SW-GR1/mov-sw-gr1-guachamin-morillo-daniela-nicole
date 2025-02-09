package com.example.gestionentidades

import java.time.LocalDate

data class PapaNoel(
    val id: Int,
    val nombre: String,
    var edad: Int,
    var peso: Double,
    val paisResidencia: String,
    val fechaInicio: LocalDate,
    //val renos: ArrayList<Reno> = arrayListOf()
    var latitud: Double, //agregado para mapa
    var longitud: Double //agregado para mapa
)