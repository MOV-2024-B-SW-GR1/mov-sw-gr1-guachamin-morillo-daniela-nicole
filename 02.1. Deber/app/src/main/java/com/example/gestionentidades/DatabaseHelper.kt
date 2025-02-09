package com.example.gestionentidades

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "gestion.db", null, 2) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE papa_noel (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nombre TEXT, " +
                    "edad INTEGER, " +
                    "peso REAL, " +
                    "pais TEXT, " +
                    "fechaInicio TEXT, " +
                    "latitud REAL DEFAULT NULL, " +
                    "longitud REAL DEFAULT NULL)"
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

    /*override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS papa_noel")
        db.execSQL("DROP TABLE IF EXISTS renos")
        onCreate(db)
    }*/

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2){
            db.execSQL("ALTER TABLE papa_noel ADD COLUMN latitud REAL DEFAULT NULL")
            db.execSQL("ALTER TABLE papa_noel ADD COLUMN longitud REAL DEFAULT NULL")
        }
    }
}
