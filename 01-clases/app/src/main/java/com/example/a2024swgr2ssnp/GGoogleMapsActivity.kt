package com.example.a2024swgr2ssnp

import android.content.pm.PackageManager
import android.icu.text.CaseMap.Title
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PackageManagerCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolygonOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.material.snackbar.Snackbar

class GGoogleMapsActivity : AppCompatActivity() {
    private lateinit var mapa: GoogleMap
    var permisos = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ggoogle_maps)
    }
    fun solicitarPermiso(){
        val contexto = this.applicationContext
        val nombrePermisoFine = android.Manifest.permission.ACCESS_FINE_LOCATION
        val nombrePersmisoCoarse=android.Manifest.permission.ACCESS_COARSE_LOCATION
        val permisoFine = ContextCompat.checkSelfPermission(contexto,nombrePermisoFine)
        val permisoCoarse= ContextCompat.checkSelfPermission(contexto,nombrePersmisoCoarse)
        val tienePermisos = permisoFine == PackageManager.PERMISSION_GRANTED &&
                permisoCoarse == PackageManager.PERMISSION_GRANTED
        if (tienePermisos){
            permisos=true
        }else{
            ActivityCompat.requestPermissions(
                this, arrayOf(nombrePermisoFine,nombrePersmisoCoarse),1
            )
        }
    }

    fun iniciarLogicaMapa(){
        val fragmentoMapa = supportFragmentManager.findFragmentById(R.id.map)as SupportMapFragment
        fragmentoMapa.getMapAsync{googleMap -> with (googleMap){
            mapa = googleMap
            establecerConfiguracionMapa()
            moverQuicentro()
            añadirPolilinea()
            añadirPoligono()
        } }
    }

    fun establecerConfiguracionMapa(){
        val contexto = this.applicationContext
        with(mapa) {
            val nombrePermisoFine = android.Manifest.permission.ACCESS_FINE_LOCATION
            val nombrePermisoCoarse = android.Manifest.permission.ACCESS_COARSE_LOCATION
            val permisoFine = ContextCompat.checkSelfPermission(contexto,nombrePermisoFine)
            val permisoCoarse = ContextCompat.checkSelfPermission(contexto,nombrePermisoCoarse)
            val tienepermisos = permisoFine == PackageManager.PERMISSION_GRANTED &&
                    permisoCoarse == PackageManager.PERMISSION_GRANTED
            if(tienepermisos){
                mapa.isMyLocationEnabled = true
                uiSettings.isMyLocationButtonEnabled= true
            }
            uiSettings.isZoomControlsEnabled=true
        }
    }


    fun moverCamaraConZoom(latLng: LatLng,zoom: Float= 10f  ){
        mapa.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                latLng,zoom
            )
        )

    }

    fun añadirMarcador(latLng: LatLng, title: String): Marker{

        return  mapa.addMarker(
            MarkerOptions().position(latLng).title(title)
        )!!

    }

    fun mostrarSnackbar(texto: String) {
        val snack = Snackbar.make(
            findViewById(R.id.cl_google_maps),
            texto,
            Snackbar.LENGTH_INDEFINITE
        )
        snack.show()
    }


    fun moverQuicentro(){
        val zoom = 17f
        val quicentro = LatLng(
            -0.1755181190138262, -78.47918808450619
        )
        val titulo = "Quicentro"
        val markQuicentro = añadirMarcador (quicentro,titulo)
        markQuicentro.tag=titulo
        moverCamaraConZoom(quicentro,zoom)
    }

    fun añadirPolilinea(){
        with(mapa){
            val polilineaUno = mapa.addPolyline(
                    PolylineOptions().clickable(true)
                        .add(
                            LatLng(-0.1758614401269219, -78.48571121689449),
                            LatLng(-0.17843634842358283, -78.48244965071446),
                            LatLng(-0.17843634842358283, -78.47927391522337)
                        )
                    )
            polilineaUno.tag="Polilinea-uno"
        }
    }

    fun añadirPoligono(){
        with(mapa){
            val poligonoUno=mapa.addPolygon(
                PolygonOptions().clickable(true)
                    .add(
                        LatLng(-0.172342398151301, -78.48596870896792),
                        LatLng(-0.17508896750495734, -78.48124802107579),
                        LatLng(-0.17345819199934728, -78.47584068767206)
                    )
            )
            poligonoUno.tag="Poligono-uno"
        }
    }
}