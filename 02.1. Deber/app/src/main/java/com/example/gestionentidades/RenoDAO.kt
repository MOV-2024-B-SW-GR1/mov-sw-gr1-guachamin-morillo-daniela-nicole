package com.example.gestionentidades

import android.content.ContentValues
import android.content.Context
import android.util.Log
import java.time.LocalDate

/*
class RenoDAO(context: Context) {
    private val dbHelper = DatabaseHelper(context)

    // Agregar Reno
    fun agregarReno(papaNoelId: Int, nombre: String, edad: Int, peso: Double, esLider: Boolean): Long {
        val db = dbHelper.writableDatabase

        if (esLider && hayLider(papaNoelId)) {
            db.close()
            return -1 // Ya existe un líder
        }

        val values = ContentValues().apply {
            put("nombre", nombre)
            put("edad", edad)
            put("peso", peso)
            put("esLider", if (esLider) 1 else 0)
            put("fechaRegistro", LocalDate.now().toString())
            put("papa_noel_id", papaNoelId)
        }
        return db.insert("renos", null, values).also { db.close() }
    }

    // Obtener renos de Papá Noel
    fun obtenerRenos(papaNoelId: Int): List<Reno> {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM renos WHERE papa_noel_id = ?", arrayOf(papaNoelId.toString()))

        val listaRenos = mutableListOf<Reno>()
        while (cursor.moveToNext()) {
            listaRenos.add(
                Reno(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getDouble(3),
                    cursor.getInt(4) == 1,
                    LocalDate.parse((cursor.getString(5)))
                )
            )
        }
        cursor.close()
        db.close()
        return listaRenos
    }

    // Actualizar Reno
    fun actualizarReno(id: Int, nuevaEdad: Int?, nuevoPeso: Double?, esLider: Boolean?) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            nuevaEdad?.let { put("edad", it) }
            nuevoPeso?.let { put("peso", it) }
            esLider?.let { put("esLider", if (it) 1 else 0) }
        }
        db.update("renos", values, "id = ?", arrayOf(id.toString()))
        db.close()
    }

    // Eliminar Reno
    fun eliminarReno(id: Int) {
        val db = dbHelper.writableDatabase
        db.delete("renos", "id = ?", arrayOf(id.toString()))
        db.close()
    }

    // Verificar si hay un líder
    fun hayLider(papaNoelId: Int): Boolean {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT COUNT(*) FROM renos WHERE papa_noel_id = ? AND esLider = 1",
            arrayOf(papaNoelId.toString()))
        cursor.moveToFirst()
        val count = cursor.getInt(0)
        cursor.close()
        db.close()
        return count > 0
    }
}
*/
class RenoDAO(context: Context) {
    private val dbHelper = DatabaseHelper(context)

    fun agregarReno(papaNoelId: Int, nombre: String, edad: Int, peso: Double, esLider: Boolean): Long {
        val db = dbHelper.writableDatabase
        try {
            if (esLider && hayLider(papaNoelId)) {
                Log.d("DEBUG", "Ya existe un líder para Papá Noel ID: $papaNoelId")
                return -1 // Ya existe un líder
            }

            val values = ContentValues().apply {
                put("nombre", nombre)
                put("edad", edad)
                put("peso", peso)
                put("esLider", if (esLider) 1 else 0)
                put("fechaIncorporacion", LocalDate.now().toString())
                put("papa_noel_id", papaNoelId)
            }

            val resultado = db.insert("renos", null, values)

            if (resultado == -1L) {
                Log.e("ERROR", "Error al insertar el reno en la base de datos")
            } else {
                Log.d("DEBUG", "Reno insertado correctamente con ID: $resultado")
            }

            return resultado
        } catch (e: Exception) {
            Log.e("ERROR", "Error al agregar reno: ${e.message}")
            return -1
        } finally {
            db.close()
        }
    }

    fun obtenerRenos(papaNoelId: Int): List<Reno> {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM renos WHERE papa_noel_id = ?", arrayOf(papaNoelId.toString()))
        val listaRenos = mutableListOf<Reno>()

        try {
            while (cursor.moveToNext()) {
                val reno = Reno(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getDouble(3),
                    cursor.getInt(4) == 1,
                    LocalDate.parse(cursor.getString(5))
                )
                Log.d("DEBUG", "Reno encontrado en DB: ${reno.nombre}, Papá Noel ID: ${cursor.getInt(6)}")
                listaRenos.add(reno)
            }
        } finally {
            cursor.close()
            db.close()
        }

        Log.d("DEBUG", "Total de renos encontrados: ${listaRenos.size}")
        return listaRenos
    }


    // Obtener todos los renos
    fun obtenerTodosRenos(): List<Reno> {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM renos", null)  // Consulta directa
        val renos = mutableListOf<Reno>()

        if (cursor.moveToFirst()) {
            do {
                val idIndex = cursor.getColumnIndex("id")
                val nombreIndex = cursor.getColumnIndex("nombre")
                val edadIndex = cursor.getColumnIndex("edad")
                val pesoIndex = cursor.getColumnIndex("peso")
                val esLiderIndex = cursor.getColumnIndex("esLider")
                val fechaIncorporacionIndex = cursor.getColumnIndex("fechaIncorporacion")

                if (idIndex >= 0 && nombreIndex >= 0 && edadIndex >= 0 && pesoIndex >= 0 && esLiderIndex >= 0 && fechaIncorporacionIndex >=0) {
                    val id = cursor.getInt(idIndex)
                    val nombre = cursor.getString(nombreIndex)
                    val edad = cursor.getInt(edadIndex)
                    val peso = cursor.getDouble(pesoIndex)
                    val esLider = cursor.getInt(esLiderIndex) == 1
                    val fechaIncoporacion = LocalDate.parse(cursor.getString(fechaIncorporacionIndex))

                    renos.add(Reno(id, nombre, edad, peso, esLider, fechaIncoporacion))
                }
            } while (cursor.moveToNext())
        }
        cursor.close()
        return renos
    }

    // Obtener un reno por su ID
    fun obtenerRenoPorId(id: Int): Reno? {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM renos WHERE id = ?", arrayOf(id.toString()))

        return try {
            if (cursor.moveToFirst()) {
                Reno(
                    cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("edad")),
                    cursor.getDouble(cursor.getColumnIndexOrThrow("peso")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("esLider")) == 1,
                    LocalDate.parse(cursor.getString(cursor.getColumnIndexOrThrow("fechaIncorporacion")))
                )
            } else null
        } finally {
            cursor.close()
            db.close()
        }
    }




    // Actualizar Reno
    /*fun actualizarReno(id: Int, nuevaEdad: Int?, nuevoPeso: Double?, esLider: Boolean?) {
        val db = dbHelper.writableDatabase
        try {
            val values = ContentValues().apply {
                nuevaEdad?.let { put("edad", it) }
                nuevoPeso?.let { put("peso", it) }
                esLider?.let { put("esLider", if (it) 1 else 0) }
            }
            db.update("renos", values, "id = ?", arrayOf(id.toString()))
        } finally {
            db.close()
        }
    }*/

    fun actualizarReno(id: Int, nuevaEdad: Int?, nuevoPeso: Double?, esLider: Boolean?) {
        val db = dbHelper.writableDatabase
        try {
            val values = ContentValues().apply {
                nuevaEdad?.let { put("edad", it) }
                nuevoPeso?.let { put("peso", it) }
                esLider?.let { put("esLider", if (it) 1 else 0) }
            }
            val filasActualizadas = db.update("renos", values, "id = ?", arrayOf(id.toString()))
            Log.d("DEBUG", "Filas actualizadas: $filasActualizadas")
        } finally {
            db.close()
        }
    }


    // Eliminar Reno
    fun eliminarReno(id: Int) {
        val db = dbHelper.writableDatabase
        try {
            db.delete("renos", "id = ?", arrayOf(id.toString()))
        } finally {
            db.close()
        }
    }

    // Verificar si hay un líder
    fun hayLider(papaNoelId: Int): Boolean {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            "SELECT COUNT(*) FROM renos WHERE papa_noel_id = ? AND esLider = 1",
            arrayOf(papaNoelId.toString())
        )
        return try {
            if (cursor.moveToFirst()) cursor.getInt(0) > 0 else false
        } finally {
            cursor.close()
            db.close()
        }
    }

}

