package com.anomdev.aristimaps

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity(), OnMapReadyCallback,
    GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener {

    private lateinit var map: GoogleMap

    companion object {
        const val REQUEST_CODE_LOCATION = 0
    }

    //Este método se llama cuando el mapa se haya cargado.
    override fun onMapReady(createdMap: GoogleMap) {
        map = createdMap
        createMarker()
        map.setOnMyLocationButtonClickListener(this)
        map.setOnMyLocationClickListener(this)
        enableLocation()
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

    //Aquí checamos que el permiso del Manifest para la localizacón es igual a un permiso aceptado
    private fun isLocationPermissionGranted() = ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED


    //Este método intentará activar la localización, si se puede se activa y si no, pedimos permisos de nuevo
    private fun enableLocation() {
        if (!::map.isInitialized) return
        if (isLocationPermissionGranted()) {
            map.isMyLocationEnabled = true
        } else {
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            Toast.makeText(this, "Ve a ajustes y acepta los permisos", Toast.LENGTH_LONG).show()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_LOCATION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE_LOCATION -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                map.isMyLocationEnabled = true
            } else {
                Toast.makeText(
                    this,
                    "Para activar la localización, ve a ajustes y acepta los permisos",
                    Toast.LENGTH_LONG
                ).show()
            }
            else -> {
            }
        }
    }

    //Aquí comprobamos cada vez que volvamos a entrar en nuestra app (onStart, onResume, etc) cómo están los permisos para evitar que de pronto no los tengamos, la app crea que sí y crashee.
    override fun onResumeFragments() {
        super.onResumeFragments()
        if (!::map.isInitialized) return
        if (!isLocationPermissionGranted()) {
            map.isMyLocationEnabled = false
            Toast.makeText(
                this,
                "Para activar la localización, ve a ajustes y acepta los permisos",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    //Cada vez que el usuario pulse en el botón de la ubicación se desarrollará lo siguiente
    override fun onMyLocationButtonClick(): Boolean {
        Toast.makeText(
            this,
            "Viajando a tu ubicación actual",
            Toast.LENGTH_LONG
        ).show()
        return false
    }

    // Este método se llamará cada vez que el usuario pulse en el marcador de mi localización en tiempo real
    override fun onMyLocationClick(p0: Location) {
        Toast.makeText(
            this,
            "Estás en ${p0.latitude}, ${p0.longitude}",
            Toast.LENGTH_LONG
        ).show()    }
}