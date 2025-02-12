package com.example.gestionentidades

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.res.ResourcesCompat

/*class ListaPapaNoelActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_papa_noel)

        val papaNoel = Funcion.obtenerPapaNoel()
        val listView = findViewById<ListView>(R.id.listViewPapaNoel)

        val adapter = if (papaNoel != null) {
            val papaNoelList = listOf(
                "Nombre: ${papaNoel.nombre}",
                "Edad: ${papaNoel.edad}",
                "Peso: ${papaNoel.peso}",
                "País: ${papaNoel.paisResidencia}"
            )
            ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                papaNoelList
            )
        } else {
            ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                listOf("No hay Papá Noel registrado")
            )
        }

        listView.adapter = adapter

        // Evento para mostrar el menú emergente al hacer clic en un ítem
        listView.setOnItemClickListener { _, view, _, _ ->
            mostrarMenu(view)
        }

        /*findViewById<Button>(R.id.btnEliminarPapaNoel).setOnClickListener {
            eliminarPapaNoel()
        }*/
    }

    // Método para mostrar el menú emergente
    private fun mostrarMenu(view: View) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.menuInflater.inflate(R.menu.menu_papanoel, popupMenu.menu)

        // Manejo de clics en las opciones del menú
        popupMenu.setOnMenuItemClickListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.actualizar -> {
                    val intent = Intent(this, ActualizarPapaNoelActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.eliminar -> {
                    eliminarPapaNoel()
                    true
                }
                R.id.gestionar_renos -> {
                    val intent = Intent(this, GestionarRenosActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

        popupMenu.show()
    }

    private fun eliminarPapaNoel() {
        Funcion.eliminarPapaNoel()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}*/

class ListaPapaNoelActivity : AppCompatActivity() {

    private lateinit var papaNoelDAO: PapaNoelDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_papa_noel)

        // Inicializar el DAO
        papaNoelDAO = PapaNoelDAO(this)
        val listView = findViewById<ListView>(R.id.listViewPapaNoel)

        val papaNoel = papaNoelDAO.obtenerPapaNoel()

        if (papaNoel != null) {
            val datos = listOf(
                "Nombre: ${papaNoel.nombre}",
                "Edad: ${papaNoel.edad}",
                "Peso: ${papaNoel.peso}",
                "País: ${papaNoel.paisResidencia}",
                "Fecha de Inicio: ${papaNoel.fechaInicio}",
                "Latitud: ${papaNoel.latitud ?: "No registrada"}",
                "Longitud: ${papaNoel.longitud ?: "No registrada"}"
            )

            val adapter = object : ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datos){
                override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                    // Obtener la vista del ítem
                    val view = super.getView(position, convertView, parent)

                    // Obtener el TextView de la vista
                    val textView = view.findViewById<TextView>(android.R.id.text1)

                    //Cargar fuente
                    val typeface = ResourcesCompat.getFont(context, R.font.subtitulos)
                    textView.typeface = typeface

                    // Cambiar el color del texto
                    textView.setTextColor(Color.WHITE)

                    // Cambiar el tamaño de la letra (si es necesario)
                    textView.textSize = 18f // Tamaño en sp

                    return view
                }
            }
            listView.adapter = adapter

            // Agregar el evento para mostrar el menú emergente
            listView.setOnItemClickListener { _, view, _, _ ->
                mostrarMenu(view, papaNoel.id)
            }
        } else {
            // Si no hay Papá Noel registrado, ir directamente a GestionarPapaNoelActivity
            startActivity(Intent(this, GestionarPapaNoelActivity::class.java))
            finish()
        }

        // Botón para ver la lista de renos
        val btnListaRenos = findViewById<Button>(R.id.btnVerRenos)
        btnListaRenos.setOnClickListener {
            val papaNoelDAO = PapaNoelDAO(this)
            val papaNoel = papaNoelDAO.obtenerPapaNoel()

            if (papaNoel != null) {
                val intent = Intent(this, ListaRenosActivity::class.java)
                intent.putExtra("papaNoelId", papaNoel.id)
                Log.d("DEBUG", "ListaRenosActivity - papaNoelId recibido: ${papaNoel.id}")
                startActivity(intent)
            } else {
                Toast.makeText(this, "No hay un Papá Noel registrado", Toast.LENGTH_SHORT).show()
            }
        }

        val btnMapa: Button = findViewById(R.id.btnMapa)
        btnMapa.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            val papaNoel = papaNoelDAO.obtenerPapaNoel()
            if (papaNoel != null) {
                intent.putExtra("LATITUD", papaNoel.latitud)
                intent.putExtra("LONGITUD", papaNoel.longitud)
                startActivity(intent)
            } else {
                Toast.makeText(this, "No hay un Papá Noel registrado", Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun mostrarMenu(view: View, papaNoelId: Int) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.menuInflater.inflate(R.menu.menu_papanoel, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.actualizar -> {
                    startActivity(Intent(this, ActualizarPapaNoelActivity::class.java))
                    true
                }
                R.id.eliminar -> {
                    papaNoelDAO.eliminarPapaNoel()
                    recreate() // Refrescar la lista después de eliminar
                    true
                }
                R.id.gestionar_renos -> {
                    val intent = Intent(this, GestionarRenosActivity::class.java)
                    intent.putExtra("papaNoelId", papaNoelId)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }
}
