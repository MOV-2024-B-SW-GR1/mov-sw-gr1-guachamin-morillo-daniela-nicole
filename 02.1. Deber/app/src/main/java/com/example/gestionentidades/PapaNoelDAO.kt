package com.example.gestionentidades

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import java.time.LocalDate

class PapaNoelDAO(context: Context) {
    private val dbHelper = DatabaseHelper(context)

    // Crear Papá Noel
    fun crearPapaNoel(nombre: String, edad: Int, peso: Double, pais: String): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nombre", nombre)
            put("edad", edad)
            put("peso", peso)
            put("pais", pais)
            put("fechaInicio", LocalDate.now().toString())
        }
        return db.insert("papa_noel", null, values).also { db.close() }
    }

    // Obtener Papá Noel
    fun obtenerPapaNoel(): PapaNoel? {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM papa_noel LIMIT 1", null)

        return if (cursor.moveToFirst()) {
            val papaNoel = PapaNoel(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getInt(2),
                cursor.getDouble(3),
                cursor.getString(4),
                LocalDate.parse(cursor.getString(5))
            )
            cursor.close()
            db.close()
            papaNoel
        } else {
            cursor.close()
            db.close()
            null
        }
    }

    // Actualizar Papá Noel
    fun actualizarPapaNoel(id: Int, nuevaEdad: Int?, nuevoPeso: Double?) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            nuevaEdad?.let { put("edad", it) }
            nuevoPeso?.let { put("peso", it) }
        }
        db.update("papa_noel", values, "id = ?", arrayOf(id.toString()))
        db.close()
    }

    // Eliminar Papá Noel
    fun eliminarPapaNoel() {
        val db = dbHelper.writableDatabase
        db.execSQL("DELETE FROM renos") // Eliminar renos primero
        db.execSQL("DELETE FROM papa_noel")
        db.close()
    }
}
