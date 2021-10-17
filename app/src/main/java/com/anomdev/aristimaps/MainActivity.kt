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
import com.google.android.gms.maps.model.*

class MainActivity : AppCompatActivity(), OnMapReadyCallback,
    GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener {

    private lateinit var map: GoogleMap

    companion object {
        const val REQUEST_CODE_LOCATION = 0

        const val LAT_LUKLA = 27.68815432701317
        const val LONG_LUKLA = 86.73012967840062

        const val LAT_PADHKING = 27.740303576902523
        const val LONG_PADHKING = 86.71254518106007

        const val LAT_NAMCHE_BAZAAR = 27.8037397596329
        const val LONG_NAMCHE_BAZAAR = 86.7095733710473

        const val LAT_TENGBOCHE = 27.836023557186515
        const val LONG_TENGBOCHE = 86.7639469783539

        const val LAT_PHERICHE = 27.895177680510063
        const val LONG_PHERICHE = 86.81974603744446

        const val LAT_LOBUCHE = 27.94853035663438
        const val LONG_LOBUCHE = 86.8104886770864

        const val LAT_GORAK_SHEP = 27.980851026496307
        const val LONG_GORAK_SHEP = 86.82890481124848

        const val LAT_KALA_PATTHAR = 27.996240111323925
        const val LONG_KALA_PATTHAR = 86.82869356123066
    }

    //Este método se llama cuando el mapa se haya cargado.
    override fun onMapReady(createdMap: GoogleMap) {
        map = createdMap
        createMarker()
        everestBaseCampTrekkingMarkers()
        createPolyLines()

        map.setOnMyLocationButtonClickListener(this)
        map.setOnMyLocationClickListener(this)
        enableLocation()
    }

    private fun createPolyLines() {
        val polylineOptions = PolylineOptions()
            .add(LatLng(LAT_LUKLA, LONG_LUKLA)) //Lukla
            .add(LatLng(LAT_PADHKING, LONG_PADHKING)) //Phadking
            .add(LatLng(LAT_NAMCHE_BAZAAR, LONG_NAMCHE_BAZAAR)) //Namche bazaar
            .add(LatLng(LAT_TENGBOCHE, LONG_TENGBOCHE)) //Tengboche
            .add(LatLng(LAT_PHERICHE, LONG_PHERICHE)) //Periche
            .add(LatLng(LAT_LOBUCHE, LONG_LOBUCHE)) //Lobuche
            .add(LatLng(LAT_GORAK_SHEP, LONG_GORAK_SHEP)) //Gorak Shep
            .add(LatLng(LAT_KALA_PATTHAR, LONG_KALA_PATTHAR)) //Kala Pattar (EBC)
            .width(8f)
            .color(ContextCompat.getColor(this, R.color.red))

        val polyline = map.addPolyline(polylineOptions)
        polyline.startCap = RoundCap()
        polyline.endCap = RoundCap()
        //polyline.endCap = CustomCap(BitmapDescriptorFactory.fromResource(R.drawable.ebc))

        polyline.isClickable = true

        map.setOnPolylineClickListener { polyline -> changeColor(polyline) }

    }

    fun changeColor(polyline: Polyline) {
        val color = (0..3).random()
        when (color) {
            0 -> polyline.color = ContextCompat.getColor(this, R.color.red)
            1 -> polyline.color = ContextCompat.getColor(this, R.color.yellow)
            2 -> polyline.color = ContextCompat.getColor(this, R.color.green)
            3 -> polyline.color = ContextCompat.getColor(this, R.color.blue)
        }
    }


    private fun createMarker() {
        //Elegimos las coordenadas
        val coordinates = LatLng(35.70679238851099, 139.64941558595015)
        //Creamos el marcador en nuestras coordenadas
        val marker = MarkerOptions().position(coordinates).title("Koenji")
        //Le pasamos al mapa nuestro nuevo marcador
        map.addMarker(marker)
        //Hacemos una animación para que haga zoom de 18 floats al marcador elegido en 4 segundos.
        /*map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(coordinates, 18f),
            4000,
            null
        )*/
    }

    private fun everestBaseCampTrekkingMarkers() {
        val coordinatesArrival = LatLng(LAT_LUKLA, LONG_LUKLA)
        val markerArrival = MarkerOptions().position(coordinatesArrival).title("ARRIVAL: Lukla (2.860m)")
        map.addMarker(markerArrival)
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(coordinatesArrival, 12f),
            4000,
            null
        )



        val coordinatesFirstDay = LatLng(LAT_PADHKING, LONG_PADHKING)
        val markerFirstDay = MarkerOptions().position(coordinatesFirstDay).title("1º DAY: Phadking (2.610m)")
        map.addMarker(markerFirstDay)

        val coordinatesSecondDay = LatLng(LAT_NAMCHE_BAZAAR, LONG_NAMCHE_BAZAAR)
        val markerSecondDay = MarkerOptions().position(coordinatesSecondDay).title("2º DAY: Namche Bazaar (3.440m) [Acc]")
        map.addMarker(markerSecondDay)

        val coordinatesFourthDay = LatLng(LAT_TENGBOCHE, LONG_TENGBOCHE)
        val markerFourthDay = MarkerOptions().position(coordinatesFourthDay).title("4º DAY: Tengboche (3.867m)")
        map.addMarker(markerFourthDay)

        val coordinatesFifthDay = LatLng(LAT_PHERICHE, LONG_PHERICHE)
        val markerFifthDay = MarkerOptions().position(coordinatesFifthDay).title("5º DAY: Pheriche(4.371m) [Acc]")
        map.addMarker(markerFifthDay)

        val coordinatesSeventhDay = LatLng(LAT_LOBUCHE, LONG_LOBUCHE)
        val markerSeventhDay = MarkerOptions().position(coordinatesSeventhDay).title("7º DAY: Lobuche (4.940m)")
        map.addMarker(markerSeventhDay)

        val coordinatesEighthDay = LatLng(LAT_GORAK_SHEP, LONG_GORAK_SHEP)
        val markerEighthDay = MarkerOptions().position(coordinatesEighthDay).title("8º DAY: Gorak Shep (5.164m)")
        map.addMarker(markerEighthDay)

        val coordinatesNinthDay = LatLng(LAT_KALA_PATTHAR, LONG_KALA_PATTHAR)
        val markerNinthDay = MarkerOptions().position(coordinatesNinthDay).title("9º DAY: Kala Patthar hill (5.644m)")
        map.addMarker(markerNinthDay)

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
        ).show()
    }
}