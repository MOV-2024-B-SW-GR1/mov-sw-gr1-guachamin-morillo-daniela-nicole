/*package com.example.gestionentidades

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
}*/

package com.example.gestionentidades

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class ActualizarRenoActivity : AppCompatActivity() {

    private lateinit var renoDAO: RenoDAO
    private var renoId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("DEBUG","ACTUALIZAR SE ESTA EJECUTANDO")
        try {
            setContentView(R.layout.activity_actualizar_reno)
            Log.d("DEBUG", "Layout cargado correctamente")
        } catch (e: Exception) {
            Log.e("ERROR", "Error al cargar el layout: ${e.message}")
        }

        renoDAO = RenoDAO(this)

        // Obtener el ID del reno que se va a actualizar
        renoId = intent.getIntExtra("renoId", -1)
        Log.d("DEBUG", "ID del reno recibido en ActualizarRenoActivity: $renoId")
        if (renoId == -1) {
            Log.d("Debug", "Error: ID de reno no encontrado")
            Toast.makeText(this, "Error: ID de reno no encontrado", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val reno = renoDAO.obtenerRenoPorId(renoId!!)
        Log.d("DEBUG", "Obteniendo reno con ID: $renoId")

        // Si encontramos el reno, cargar los datos en los campos
        if (reno != null) {
            Log.d("DEBUG","RENO ENCONTRADO; ${reno.nombre}")
        } else {
            Log.d("DEBUG","RENO NO ENCONTRADO")
            Toast.makeText(this, "Error: Reno no encontrado", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    fun actualizarReno(view: View) {
        val edad = findViewById<EditText>(R.id.edtNuevaEdadReno).text.toString().toIntOrNull()
        val peso = findViewById<EditText>(R.id.edtNuevoPesoReno).text.toString().toDoubleOrNull()
        val esLider = findViewById<CheckBox>(R.id.chkNuevoLider).isChecked

        if (edad == null || peso == null) {
            Toast.makeText(this, "Error: Edad y peso deben ser números válidos", Toast.LENGTH_SHORT).show()
            return
        }

        if (renoId != null) {
            renoDAO.actualizarReno(renoId!!, edad, peso, esLider)
            Toast.makeText(this, "Reno actualizado", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ListaRenosActivity::class.java)
            startActivity(intent)
            setResult(RESULT_OK)
        } else {
            Toast.makeText(this, "Error: No se pudo actualizar el reno", Toast.LENGTH_SHORT).show()
        }
    }
}
