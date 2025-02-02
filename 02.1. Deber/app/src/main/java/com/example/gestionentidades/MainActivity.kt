package com.example.gestionentidades

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView


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
            if (existePapaNoel()) {
                abrirListaPapaNoel()  // Si existe Papa Noel, abre la lista
            } else {
                gestionarPapaNoel()   // Si no existe, gestiona el Papa Noel
            }
        }
    }

    private fun existePapaNoel(): Boolean {
        val db = databaseHelper.readableDatabase
        val cursor = db.rawQuery("SELECT COUNT(*) FROM papa_noel", null)
        var existe = false

        if (cursor.moveToFirst()) {
            existe = cursor.getInt(0) > 0
        }

        cursor.close()
        db.close()
        return existe
    }

    private fun abrirListaPapaNoel() {
        val intent = Intent(this, ListaPapaNoelActivity::class.java)
        startActivity(intent)
        finish() // Cierra esta actividad para evitar volver atrás
    }

    private fun gestionarPapaNoel() {
        val intent = Intent(this, GestionarPapaNoelActivity::class.java)
        startActivity(intent)
    }
}
