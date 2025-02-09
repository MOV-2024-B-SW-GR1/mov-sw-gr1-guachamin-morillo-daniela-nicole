package com.example.gestionentidades

import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast


class MainActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var gestionarButton: Button  // Referencia al botón gestionar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializa el helper de base de datos
        databaseHelper = DatabaseHelper(this)

        // Referencia al botón de gestionar
        gestionarButton = findViewById(R.id.btnGestionarPapaNoel)

        // Escucha el clic en el botón de gestionar
        gestionarButton.setOnClickListener {
            try {
                if (existePapaNoel()) {
                    abrirListaPapaNoel()  // Si existe Papa Noel, abre la lista
                } else {
                    gestionarPapaNoel()   // Si no existe, gestiona el Papa Noel
                }
            } catch (e: Exception) {
                Log.e("MainActivity", "Error al gestionar Papá Noel", e)
                Toast.makeText(this, "Hubo un error. Intenta de nuevo.", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun existePapaNoel(): Boolean {
        val db = databaseHelper.readableDatabase
        var cursor: Cursor? = null
        var existe = false

        try {
            cursor = db.rawQuery("SELECT COUNT(*) FROM papa_noel", null)
            if (cursor.moveToFirst()) {
                existe = cursor.getInt(0) > 0
            }
        } catch (e: Exception) {
            Log.e("MainActivity", "Error al consultar la base de datos", e)
        } finally {
            cursor?.close()
            db.close()
        }
        return existe
    }


    private fun abrirListaPapaNoel() {
        Log.d("MainActivity", "Abriendo ListaPapaNoelActivity...")
        val intent = Intent(this, ListaPapaNoelActivity::class.java)
        startActivity(intent)
        finish() // Cierra esta actividad para evitar volver atrás
    }

    private fun gestionarPapaNoel() {
        Log.d("MainActivity", "Abriendo GestionarPapaNoelActivity...")
        val intent = Intent(this, GestionarPapaNoelActivity::class.java)
        startActivity(intent)
    }
}
