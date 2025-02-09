package com.example.proyecto

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView

class ForoAdapter(private val context: Context, private val foros: ArrayList<Pair<String, String>>) : BaseAdapter() {

    private val respuestasMap = mutableMapOf<Int, ArrayList<String>>() // Para almacenar respuestas por foro

    override fun getCount(): Int = foros.size
    override fun getItem(position: Int): Pair<String, String> = foros[position]
    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_foro_maternidad, parent, false)

        val tvUsuario = view.findViewById<TextView>(R.id.tvUsuario)
        val tvMensaje = view.findViewById<TextView>(R.id.tvMensaje)
        val btnResponder = view.findViewById<Button>(R.id.btnResponder)
        val listViewRespuestas = view.findViewById<ListView>(R.id.listViewRespuestas)

        val foro = getItem(position)
        tvUsuario.text = foro.first
        tvMensaje.text = foro.second

        // Inicializar respuestas si no existen
        if (!respuestasMap.containsKey(position)) {
            respuestasMap[position] = ArrayList()
        }

        val respuestasAdapter = RespuestaAdapter(context, respuestasMap[position]!!)
        listViewRespuestas.adapter = respuestasAdapter

        // Mostrar respuestas si existen
        if (respuestasMap[position]!!.isNotEmpty()) {
            listViewRespuestas.visibility = View.VISIBLE
        }

        btnResponder.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            val input = EditText(context)
            builder.setTitle("Escribe tu respuesta")
            builder.setView(input)

            builder.setPositiveButton("Responder") { _, _ ->
                val respuesta = input.text.toString().trim()
                if (respuesta.isNotEmpty()) {
                    respuestasMap[position]!!.add(respuesta)
                    respuestasAdapter.notifyDataSetChanged()
                    listViewRespuestas.visibility = View.VISIBLE
                }
            }
            builder.setNegativeButton("Cancelar", null)
            builder.show()
        }

        return view
    }
}
