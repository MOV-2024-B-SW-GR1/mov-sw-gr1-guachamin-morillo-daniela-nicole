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
    private var latitud: Double = 66.5406 // Polo Norte
    private var longitud: Double = 25.7111 // Polo Norte

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        // Obtener las coordenadas (Polo Norte)
        val latitud = intent.getDoubleExtra("LATITUD", 66.5406)
        val longitud = intent.getDoubleExtra("LONGITUD", 25.7111)

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
