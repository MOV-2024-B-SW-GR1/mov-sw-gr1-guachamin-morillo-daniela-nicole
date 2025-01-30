package com.example.gestionentidades

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun gestionarPapaNoel(view: View) {
        val intent = Intent(this, GestionarPapaNoelActivity::class.java)
        startActivity(intent)
    }
}
