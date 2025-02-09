package com.example.proyecto

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ForoMaternidadActivity : AppCompatActivity() {
    private lateinit var layoutForos: LinearLayout
    private lateinit var etNuevoForo: EditText
    private lateinit var btnAgregarForo: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_foro_maternidad)

        layoutForos = findViewById(R.id.layoutForos)
        etNuevoForo = findViewById(R.id.etNuevoForo)
        btnAgregarForo = findViewById(R.id.btnAgregarForo)

        btnAgregarForo.setOnClickListener {
            val mensaje = etNuevoForo.text.toString().trim()
            if (mensaje.isNotEmpty()) {
                agregarForo("Usuario", mensaje)
                etNuevoForo.text.clear()
            }
        }
    }

    private fun agregarForo(usuario: String, mensaje: String) {
        val nuevoForo = layoutInflater.inflate(R.layout.item_foro_maternidad, null)

        val tvUsuario = nuevoForo.findViewById<TextView>(R.id.tvUsuario)
        val tvMensaje = nuevoForo.findViewById<TextView>(R.id.tvMensaje)
        val btnResponder = nuevoForo.findViewById<Button>(R.id.btnResponder)

        tvUsuario.text = usuario
        tvMensaje.text = mensaje

        btnResponder.setOnClickListener {
            // Aquí puedes abrir una nueva actividad o mostrar respuestas
            Toast.makeText(this, "Responder a: $mensaje", Toast.LENGTH_SHORT).show()
        }

        layoutForos.addView(nuevoForo, 0) // Agrega el nuevo foro al inicio de la lista
    }
}