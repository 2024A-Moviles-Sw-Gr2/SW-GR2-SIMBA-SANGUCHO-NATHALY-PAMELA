package com.example.deber02

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
class MapaActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    var permisos = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapa)

        solicitarPermisos()

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val btnBackToMain = findViewById<Button>(R.id.btn_back_to_main)
        btnBackToMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val pastelerias = BaseDeDatos.tablaPastelerias!!.obtenerPastelerias()
        for (pasteleria in pastelerias) {
            val latLng = LatLng(pasteleria.latitud ?: 0.0, pasteleria.longitud ?: 0.0)
            mMap.addMarker(MarkerOptions().position(latLng).title(pasteleria.nombrePasteleria))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
        }
    }

    fun solicitarPermisos() {
        val contexto = this.applicationContext
        val nombrePermisoFine = android.Manifest.permission.ACCESS_FINE_LOCATION
        val nombrePermisoCoarse = android.Manifest.permission.ACCESS_COARSE_LOCATION
        val permisoFine = ContextCompat.checkSelfPermission(contexto, nombrePermisoFine)
        val permisoCoarse = ContextCompat.checkSelfPermission(contexto, nombrePermisoCoarse)
        val tienePermisos = permisoFine == PackageManager.PERMISSION_GRANTED &&
                permisoCoarse == PackageManager.PERMISSION_GRANTED
        if (tienePermisos) {
            permisos = true
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(nombrePermisoFine, nombrePermisoCoarse), 1
            )
        }
    }
}