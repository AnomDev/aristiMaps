package com.anomdev.aristimaps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createFragment()

    }

    private fun createFragment() {
        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_map) as SupportMapFragment

        mapFragment.getMapAsync(this)
    }

    //Este método se llama cuando el mapa se haya cargado.
    override fun onMapReady(createdMap: GoogleMap) {
        map = createdMap
        createMarker()
    }

    private fun createMarker() {
        //Elegimos las coordenadas
        val coordinates = LatLng(35.70679238851099, 139.64941558595015)
        //Creamos el marcador en nuestras coordenadas
        val marker = MarkerOptions().position(coordinates).title("Koenji")
        //Le pasamos al mapa nuestro nuevo marcador
        map.addMarker(marker)
        //Hacemos una animación para que haga zoom de 18 floats al marcador elegido en 4 segundos.
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(coordinates, 18f),
            4000,
            null
        )
    }
}