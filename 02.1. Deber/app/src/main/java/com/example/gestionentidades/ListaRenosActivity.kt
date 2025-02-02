package com.example.gestionentidades

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

/*class ListaRenosActivity : AppCompatActivity() {

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
}*/

class ListaRenosActivity : AppCompatActivity() {

    private lateinit var renoDAO: RenoDAO
    private lateinit var papaNoelDAO: PapaNoelDAO
    private lateinit var renos: List<Reno>
    private var papaNoelId: Int = -1

    companion object {
        const val REQUEST_UPDATE_RENO = 1  // Identificador para la solicitud de actualización
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_renos)

        renoDAO = RenoDAO(this)
        papaNoelDAO = PapaNoelDAO(this)

        // Obtener el ID de Papá Noel
        papaNoelId = intent.getIntExtra("papaNoelId", -1)
        Log.d("DEBUG", "ListaRenosActivity - papaNoelId recibido: $papaNoelId")

        if (papaNoelId == -1) {
            Toast.makeText(this, "Error: No se pudo obtener el ID de Papá Noel", Toast.LENGTH_SHORT)
                .show()
            finish()
            return
        }

        cargarListaRenos()
    }

    /*    private fun cargarListaRenos() {
        val listView = findViewById<ListView>(R.id.listViewRenos)

        val papaNoel = papaNoelDAO.obtenerPapaNoel()
        renos = renoDAO.obtenerRenos(papaNoelId) // Ahora sí obtenemos los renos correctamente

        Log.d("DEBUG", "Total de renos cargados en la lista: ${renos.size}")

        val items = mutableListOf<String>()
        if (papaNoel != null) {
            items.add("Papá Noel: ${papaNoel.nombre}")
        }
        if (renos.isEmpty()) {
            items.add("No hay renos registrados.")
        } else {
            for (reno in renos) {
                Log.d("DEBUG", "Agregando reno a la lista: ${reno.nombre}")
                items.add("Reno: ${reno.nombre}")
            }
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            val nombreSeleccionado = items[position]
            if (nombreSeleccionado.startsWith("Reno:")) {
                val renoSeleccionado = renos.find { "Reno: ${it.nombre}" == nombreSeleccionado }
                renoSeleccionado?.let {
                    mostrarOpcionesReno(it)
                }
            }
        }
    }*/

    private fun cargarListaRenos() {
        val listView = findViewById<ListView>(R.id.listViewRenos)

        val papaNoel = papaNoelDAO.obtenerPapaNoel()
        renos = renoDAO.obtenerRenos(papaNoelId)

        Log.d("DEBUG", "Total de renos cargados en la lista: ${renos.size}")

        val items = mutableListOf<String>()
        val renosList = mutableListOf<Reno>()

        if (papaNoel != null) {
            items.add("Papá Noel: ${papaNoel.nombre}") // Encabezado
        }
        if (renos.isEmpty()) {
            items.add("No hay renos registrados.")
        } else {
            for (reno in renos) {
                Log.d("DEBUG", "Agregando reno a la lista: ${reno.nombre}")
                val renoInfo = "Reno: ${reno.nombre}\nEdad: ${reno.edad}\nPeso: ${reno.peso}kg\nLíder: ${if (reno.esLider) "Sí" else "No"}"
                items.add(renoInfo)
                renosList.add(reno)  // Guardamos el objeto en una lista separada
            }
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            if (position == 0 && papaNoel != null) {
                // Ignorar el encabezado "Papá Noel"
                return@setOnItemClickListener
            }
            val renoSeleccionado = renosList[position - if (papaNoel != null) 1 else 0] // Ajuste del índice
            Log.d("DEBUG", "Reno seleccionado: ${renoSeleccionado.nombre}")
            mostrarOpcionesReno(renoSeleccionado)
        }
    }

    private fun mostrarOpcionesReno(reno: Reno) {
        val opciones = arrayOf("Actualizar", "Eliminar")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Selecciona una opción")
            .setItems(opciones) { _, which ->
                when (which) {
                    0 -> actualizarReno(reno)
                    1 -> eliminarReno(reno)
                }
            }
            .show()
    }

    private fun actualizarReno(reno: Reno) {
        Log.d("DEBUG", "Intentando lanzar la actividad de actualización para el Reno con id: ${reno.id}")
        if (reno.id == null || reno.id == 0){
            Log.e("DEBUG", "ID RENO NO VÁLIDO")
            return
        }else{
            val intent = Intent(this, ActualizarRenoActivity::class.java)
            intent.putExtra("renoId", reno.id)
            startActivityForResult(intent, REQUEST_UPDATE_RENO)  // Usamos startActivityForResult para manejar el resultado de la actualización
            //startActivity(intent)
        }
    }

    // Usamos onActivityResult para actualizar la lista cuando la actividad de actualización finalice
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_UPDATE_RENO && resultCode == RESULT_OK) {
            cargarListaRenos() // Recargar la lista después de la actualización
        }
    }

    private fun eliminarReno(reno: Reno) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("¿Estás seguro de que deseas eliminar a este reno?")
            .setPositiveButton("Sí") { _, _ ->
                renoDAO.eliminarReno(reno.id)
                Toast.makeText(this, "Reno eliminado", Toast.LENGTH_SHORT).show()
                cargarListaRenos() // Recargar la lista después de eliminar
            }
            .setNegativeButton("No", null)
            .show()
    }




}
