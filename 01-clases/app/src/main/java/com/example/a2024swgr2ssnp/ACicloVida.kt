package com.example.a2024swgr2ssnp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar

class ACicloVida : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aciclo_vida)
        mostrarSnackbar("onCreate")
    }

    var textoGlobal = ""
    fun mostrarSnackbar(texto:String){
        textoGlobal+=texto
        val snack = Snackbar.make(
            findViewById(R.id.cl_ciclo_vida),
            textoGlobal,
            Snackbar.LENGTH_INDEFINITE
        )
        snack.show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState
            .run {
                // GUARDAR LAS PRIMITIVAS
                putString("variableTextoGuardado", textoGlobal)
            }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {

        super.onRestoreInstanceState(savedInstanceState)
        //Recuperar las variables
        val textoRecuperadoVariable :String? = savedInstanceState.getString("variableTextoGuardado")
        if (textoRecuperadoVariable!=null) {
            mostrarSnackbar(textoRecuperadoVariable)
            textoGlobal=textoRecuperadoVariable
        }
    }

    override fun onStart() {
        super.onStart()
        mostrarSnackbar("onStart")
    }

    override fun onResume() {
        super.onResume()
        mostrarSnackbar("onResume")
    }

    override fun onRestart() {
        super.onRestart()
        mostrarSnackbar("onRestart")
    }

    override fun onPause() {
        super.onPause()
        mostrarSnackbar("onPause")
    }

    override fun onStop() {
        super.onStop()
        mostrarSnackbar("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        mostrarSnackbar("onDestroy")
    }
}