package com.example.a2024swgr2ssnp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar

class CIntentExplicitoParametros : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cintent_explicito_parametros)
        val nombre = intent.getStringExtra("nombre")
        val apellido = intent.getStringExtra("apellido")
        val edad = intent.getIntExtra("edad",0)
        val entrenador = intent.getParcelableExtra<BEntrenador>("entrenador")
        val boton = findViewById<Button>(R.id.btn_devolver_respuesta)
        boton.setOnClickListener { devolverRespuesta() }
    }
    fun devolverRespuesta(){
        val intDevolverRespuesta = Intent()
        intDevolverRespuesta.putExtra("nombreModificado","Vicente")
        // devolver mas parametros si queremos
        setResult(RESULT_OK,intDevolverRespuesta)
        finish()
    }

}

