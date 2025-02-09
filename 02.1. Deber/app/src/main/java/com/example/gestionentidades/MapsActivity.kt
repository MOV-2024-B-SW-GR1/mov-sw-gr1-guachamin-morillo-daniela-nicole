package com.example.gestionentidades

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var papaNoelDAO: PapaNoelDAO
    private var latitud: Double = 90.0 // Polo Norte
    private var longitud: Double = 0.0 // Polo Norte


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        // Inicializar el DAO
        papaNoelDAO = PapaNoelDAO(this)

        /*Obtener las coordenadas (Polo Norte)
        //val latitud = intent.getDoubleExtra("LATITUD", 66.5406)
        //val longitud = intent.getDoubleExtra("LONGITUD", 25.7111)*/

        // Obtener las coordenadas de Papá Noel desde el DAO
        val papaNoel = papaNoelDAO.obtenerPapaNoel()
        // Si se encontró un Papa Noel con coordenadas, actualizar las variables
        papaNoel?.let {
            latitud = it.latitud
            longitud = it.longitud
        }

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Crear un LatLng con las coordenadas del Polo Norte
        val poloNorte = LatLng(latitud, longitud)

        // Mover el mapa y añadir un marcador
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(poloNorte, 5f))
        mMap.addMarker(MarkerOptions().position(poloNorte).title("Casa de Santa Claus"))
    }
}
