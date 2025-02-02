package com.example.gestionentidades

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "gestion.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE papa_noel (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nombre TEXT, " +
                    "edad INTEGER, " +
                    "peso REAL, " +
                    "pais TEXT, " +
                    "fechaInicio TEXT)"
        )

        db.execSQL(
            "CREATE TABLE renos (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nombre TEXT, " +
                    "edad INTEGER, " +
                    "peso REAL, " +
                    "esLider INTEGER, " +
                    "fechaIncorporacion TEXT, " +
                    "papa_noel_id INTEGER, " +
                    "FOREIGN KEY(papa_noel_id) REFERENCES papa_noel(id))"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS papa_noel")
        db.execSQL("DROP TABLE IF EXISTS renos")
        onCreate(db)
    }
}
