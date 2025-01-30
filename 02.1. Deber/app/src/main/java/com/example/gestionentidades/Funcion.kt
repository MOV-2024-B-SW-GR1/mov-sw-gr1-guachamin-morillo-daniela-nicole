package com.example.gestionentidades

import java.time.LocalDate

object Funcion {
    private var papaNoel: PapaNoel? = null // Solo puede haber un Papá Noel

    // Crear Papá Noel si no existe
    fun crearPapaNoel(nombre: String, edad: Int, peso: Double, pais: String) {
        if (papaNoel == null) {
            papaNoel = PapaNoel(nombre, edad, peso, pais, LocalDate.now())
            println("Papá Noel creado correctamente.")
        } else {
            println("Ya existe un Papá Noel registrado.")
        }
    }

    // Verificar si hay un Papá Noel
    fun hayPapaNoel(): Boolean {
        return papaNoel != null
    }

    // Obtener Papá Noel para la interfaz
    fun obtenerPapaNoel(): PapaNoel? {
        return papaNoel
    }

    // Actualizar los datos de Papá Noel
    fun actualizarPapaNoel(nuevaEdad: Int?, nuevoPeso: Double?) {
        if (papaNoel == null) {
            println("Primero debes registrar un Papá Noel.")
            return
        }

        nuevaEdad?.let { if (it > 0) papaNoel!!.edad = it }
        nuevoPeso?.let { if (it > 0) papaNoel!!.peso = it }
        println("Papá Noel actualizado correctamente.")
    }

    // Eliminar Papá Noel y sus renos
    fun eliminarPapaNoel() {
        papaNoel = null
        println("Papá Noel y sus renos han sido eliminados.")
    }

    // Gestionar renos
    fun agregarReno(nombre: String, edad: Int, peso: Double, esLider: Boolean) {
        if (papaNoel == null) {
            println("Primero debes registrar un Papá Noel.")
            return
        }

        if (esLider && hayLider()) {
            println("Ya existe un líder. No puedes agregar otro.")
            return
        }

        val nuevoReno = Reno(nombre, edad, peso, esLider, LocalDate.now())
        papaNoel!!.renos.add(nuevoReno)
        println("Reno agregado correctamente.")
    }

    fun actualizarReno(renoNombre: String, nuevaEdad: Int?, nuevoPeso: Double?, esLider: Boolean?) {
        if (papaNoel == null) {
            println("Primero debes registrar un Papá Noel.")
            return
        }

        val reno = papaNoel!!.renos.find { it.nombre == renoNombre }
        if (reno == null) {
            println("No se encontró un reno con el nombre $renoNombre.")
            return
        }

        nuevaEdad?.let { reno.edad = it }
        nuevoPeso?.let { reno.peso = it }

        if (esLider != null) {
            if (esLider && hayLider() && !reno.esLider) {
                println("Ya existe un líder. No puedes asignar otro.")
                return
            }
            reno.esLider = esLider
        }

        println("Reno actualizado correctamente.")
    }

    fun eliminarReno(nombre: String) {
        if (papaNoel == null) {
            println("Primero debes registrar un Papá Noel.")
            return
        }

        val eliminado = papaNoel!!.renos.removeIf { it.nombre == nombre }
        if (eliminado) {
            println("Reno eliminado correctamente.")
        } else {
            println("No se encontró un reno con el nombre $nombre.")
        }
    }

    // Obtener lista de renos
    fun obtenerRenos(): List<Reno> {
        return papaNoel?.renos ?: emptyList()
    }

    // Verificar si hay un líder entre los renos
    fun hayLider(): Boolean {
        return papaNoel?.renos?.any { it.esLider } == true
    }
}
