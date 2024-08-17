package com.example.sistema_financiero

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SalirApp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salir_app)

        // Espera de 3 segundos antes de cerrar la aplicación
        Handler(Looper.getMainLooper()).postDelayed({
            finishAffinity() // Cierra todas las actividades y finaliza la aplicación
        }, 3000) // 3000 milisegundos = 3 segundos
    }
}
