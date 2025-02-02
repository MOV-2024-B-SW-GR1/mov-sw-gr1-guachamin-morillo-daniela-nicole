package com.example.gestionentidades

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDate

/*
class GestionarPapaNoelActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestionar_papa_noel)

        // Acceder al Papá Noel si ya existe
        if (Funcion.hayPapaNoel()) {
            // Mostrar los datos del Papá Noel
            val papaNoel = Funcion.obtenerPapaNoel()
            findViewById<EditText>(R.id.edtNombre).setText(papaNoel?.nombre)
            findViewById<EditText>(R.id.edtEdad).setText(papaNoel?.edad.toString())
            findViewById<EditText>(R.id.edtPeso).setText(papaNoel?.peso.toString())
            findViewById<EditText>(R.id.edtPais).setText(papaNoel?.paisResidencia)
        }
    }

    fun guardarPapaNoel(view: View) {
        val nombre = findViewById<EditText>(R.id.edtNombre).text.toString()
        val edad = findViewById<EditText>(R.id.edtEdad).text.toString().toIntOrNull() ?: return
        val peso = findViewById<EditText>(R.id.edtPeso).text.toString().toDoubleOrNull() ?: return
        val pais = findViewById<EditText>(R.id.edtPais).text.toString()

        // Verificar si los campos son válidos antes de guardar
        if (nombre.isNotBlank() && edad != null && peso != null && pais.isNotBlank()) {
            // Crear Papá Noel usando el Singleton
            Funcion.crearPapaNoel(nombre, edad, peso, pais)
            Log.d("GestionarPapaNoelActivity", "Papá Noel guardado: $nombre, $edad, $peso, $pais")

            // Navegar a la actividad ListaPapaNoelActivity
            val intent = Intent(this, ListaPapaNoelActivity::class.java)
            startActivity(intent)
        } else {
            Log.d("GestionarPapaNoelActivity", "Datos inválidos para crear Papá Noel.")
        }
    }
}
*/

/*class GestionarPapaNoelActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestionar_papa_noel)

        dbHelper = DatabaseHelper(this)

        val papaNoel = dbHelper.obtenerPapaNoel()
        if (papaNoel != null) {
            findViewById<EditText>(R.id.edtNombre).setText(papaNoel.nombre)
            findViewById<EditText>(R.id.edtEdad).setText(papaNoel.edad.toString())
            findViewById<EditText>(R.id.edtPeso).setText(papaNoel.peso.toString())
            findViewById<EditText>(R.id.edtPais).setText(papaNoel.paisResidencia)
        }
    }

    fun guardarPapaNoel(view: View) {
        val nombre = findViewById<EditText>(R.id.edtNombre).text.toString()
        val edad = findViewById<EditText>(R.id.edtEdad).text.toString().toIntOrNull() ?: return
        val peso = findViewById<EditText>(R.id.edtPeso).text.toString().toDoubleOrNull() ?: return
        val pais = findViewById<EditText>(R.id.edtPais).text.toString()
        val fechaInicio = LocalDate.now() // Tomamos la fecha actual

        if (nombre.isNotBlank() && edad > 0 && peso > 0 && pais.isNotBlank()) {
            dbHelper.insertarPapaNoel(nombre, edad, peso, pais, fechaInicio)
            Toast.makeText(this, "Papá Noel guardado", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, ListaPapaNoelActivity::class.java))
        } else {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
        }
    }
}*/

class GestionarPapaNoelActivity : AppCompatActivity() {

    private lateinit var papaNoelDAO: PapaNoelDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestionar_papa_noel)

        // Inicializar el DAO
        papaNoelDAO = PapaNoelDAO(this)

        // Obtener los datos actuales de Papá Noel si ya existe
        val papaNoel = papaNoelDAO.obtenerPapaNoel()
        papaNoel?.let {
            // Rellenar los campos con los datos actuales de Papá Noel
            findViewById<EditText>(R.id.edtNombre).setText(it.nombre)
            findViewById<EditText>(R.id.edtEdad).setText(it.edad.toString())
            findViewById<EditText>(R.id.edtPeso).setText(it.peso.toString())
            findViewById<EditText>(R.id.edtPais).setText(it.paisResidencia)
        }
    }

    fun guardarPapaNoel(view: View) {
        val nombre = findViewById<EditText>(R.id.edtNombre).text.toString()
        val edad = findViewById<EditText>(R.id.edtEdad).text.toString().toIntOrNull() ?: return
        val peso = findViewById<EditText>(R.id.edtPeso).text.toString().toDoubleOrNull() ?: return
        val pais = findViewById<EditText>(R.id.edtPais).text.toString()

        // Verificar si los campos son válidos antes de guardar
        if (nombre.isNotBlank() && edad > 0 && peso > 0 && pais.isNotBlank()) {
            // Crear o actualizar Papá Noel utilizando el DAO
            papaNoelDAO.crearPapaNoel(nombre, edad, peso, pais)
            Toast.makeText(this, "Papá Noel guardado", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, ListaPapaNoelActivity::class.java))
        } else {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
        }
    }
}


