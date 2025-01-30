package com.example.gestionentidades

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class ActualizarRenoActivity : AppCompatActivity() {

    private var renoNombre: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar_reno)

        renoNombre = intent.getStringExtra("renoNombre")
        val reno = Funcion.obtenerRenos().find { it.nombre == renoNombre }

        if (reno != null) {
            findViewById<EditText>(R.id.edtNuevaEdadReno).setText(reno.edad.toString())
            findViewById<EditText>(R.id.edtNuevoPesoReno).setText(reno.peso.toString())
            findViewById<CheckBox>(R.id.chkNuevoLider).isChecked = reno.esLider
        }
    }

    fun actualizarReno(view: View) {
        val nuevaEdad = findViewById<EditText>(R.id.edtNuevaEdadReno).text.toString().toIntOrNull()
        val nuevoPeso = findViewById<EditText>(R.id.edtNuevoPesoReno).text.toString().toDoubleOrNull()
        val esLider = findViewById<CheckBox>(R.id.chkNuevoLider).isChecked

        if (renoNombre != null && nuevaEdad != null && nuevoPeso != null) {
            Funcion.actualizarReno(renoNombre!!, nuevaEdad, nuevoPeso, esLider)
            val intent = Intent(this, ListaRenosActivity::class.java)
            startActivity(intent)
        }
    }
}
