package com.example.gestionentidades

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class ActualizarPapaNoelActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar_papa_noel)
    }

    fun actualizarPapaNoel(view: View) {
        val nuevaEdad = findViewById<EditText>(R.id.edtNuevaEdad).text.toString().toIntOrNull()
        val nuevoPeso = findViewById<EditText>(R.id.edtNuevoPeso).text.toString().toDoubleOrNull()

        Funcion.actualizarPapaNoel(nuevaEdad, nuevoPeso)

        val intent = Intent(this, ListaPapaNoelActivity::class.java)
        startActivity(intent)
    }
}
