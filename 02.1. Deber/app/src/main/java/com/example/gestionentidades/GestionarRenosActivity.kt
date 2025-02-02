package com.example.gestionentidades

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

// GestionarRenosActivity.kt
/*class GestionarRenosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestionar_renos)
    }

    fun guardarReno(view: View) {
        val nombre = findViewById<EditText>(R.id.edtNombreReno).text.toString()
        val edad = findViewById<EditText>(R.id.edtEdadReno).text.toString().toInt()
        val peso = findViewById<EditText>(R.id.edtPesoReno).text.toString().toDouble()
        val esLider = findViewById<CheckBox>(R.id.chkLider).isChecked

        Funcion.agregarReno(nombre, edad, peso, esLider)

        val intent = Intent(this, ListaRenosActivity::class.java)
        startActivity(intent)
    }
}*/

class GestionarRenosActivity : AppCompatActivity() {

    private lateinit var renoDAO: RenoDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestionar_renos)

        // Inicializar el DAO
        renoDAO = RenoDAO(this)
    }

    fun guardarReno(view: View) {
        val nombre = findViewById<EditText>(R.id.edtNombreReno).text.toString()
        val edad = findViewById<EditText>(R.id.edtEdadReno).text.toString().toInt()
        val peso = findViewById<EditText>(R.id.edtPesoReno).text.toString().toDouble()
        val esLider = findViewById<CheckBox>(R.id.chkLider).isChecked

        val papaNoelId = 1 // Esto debe ser dinámico dependiendo del Papá Noel

        val resultado = renoDAO.agregarReno(papaNoelId, nombre, edad, peso, esLider)
        if (resultado == -1L) {
            Toast.makeText(this, "Error al agregar reno", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Reno agregado con éxito", Toast.LENGTH_SHORT).show()
        }

        val intent = Intent(this, ListaRenosActivity::class.java)
        startActivity(intent)
    }
}

