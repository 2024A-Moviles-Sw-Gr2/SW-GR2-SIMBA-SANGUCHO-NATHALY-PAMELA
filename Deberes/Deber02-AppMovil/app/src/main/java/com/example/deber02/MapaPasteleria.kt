package com.example.deber02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback

class MapaPasteleria : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var txtLatitude: EditText
    private lateinit var txtLongitude: EditText
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapa_pasteleria)
        txtLatitude = findViewById(R.id.txtLatitud)
        txtLongitude = findViewById(R.id.txtLongitud)
    }

    override fun onMapReady(p0: GoogleMap) {
        TODO("Not yet implemented")
    }
}