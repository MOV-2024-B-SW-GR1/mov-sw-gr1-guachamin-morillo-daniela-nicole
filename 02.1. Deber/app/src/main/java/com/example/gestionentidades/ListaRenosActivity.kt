package com.example.gestionentidades

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class ListaRenosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_renos)

        val renos = Funcion.obtenerRenos()
        val listView = findViewById<ListView>(R.id.listViewRenos)

        // Adaptador para mostrar la lista de renos
        val adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1,
            renos.map { it.nombre }  // Usamos solo el nombre del reno para mostrar
        )
        listView.adapter = adapter

        // Acción al seleccionar un reno de la lista
        listView.setOnItemClickListener { _, _, position, _ ->
            val renoSeleccionado = renos[position]

            // Crear un diálogo con las opciones de Actualizar y Eliminar
            val opciones = arrayOf("Actualizar", "Eliminar")
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Selecciona una opción")
                .setItems(opciones) { dialog, which ->
                    when (which) {
                        0 -> {
                            // Opción Actualizar
                            actualizarReno(renoSeleccionado)
                        }
                        1 -> {
                            // Opción Eliminar
                            eliminarReno(renoSeleccionado)
                        }
                    }
                }
                .show()
        }
    }

    // Función para actualizar el reno
    private fun actualizarReno(reno: Reno) {
        val intent = Intent(this, ActualizarRenoActivity::class.java)
        intent.putExtra("renoNombre", reno.nombre)  // Mandamos el nombre para buscar al reno
        startActivity(intent)
    }

    // Función para eliminar un reno
    private fun eliminarReno(reno: Reno) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("¿Estás seguro de que quieres eliminar a ${reno.nombre}?")
            .setPositiveButton("Sí") { _, _ ->
                Funcion.eliminarReno(reno.nombre)  // Eliminamos por nombre
                Toast.makeText(this, "Reno eliminado correctamente", Toast.LENGTH_SHORT).show()
                refreshListView()  // Actualizamos la lista de renos
            }
            .setNegativeButton("No", null)
            .show()
    }

    // Función para refrescar la lista de renos después de una acción
    private fun refreshListView() {
        val renos = Funcion.obtenerRenos()
        val listView = findViewById<ListView>(R.id.listViewRenos)
        val adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1,
            renos.map { it.nombre }  // Usamos solo el nombre del reno para mostrar
        )
        listView.adapter = adapter
    }
}
