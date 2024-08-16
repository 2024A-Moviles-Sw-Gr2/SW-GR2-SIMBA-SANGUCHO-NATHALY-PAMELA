package com.example.deber02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class PasteleriaCRUD : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pasteleria_crud)
        val idPasteleria = intent.getIntExtra("id", 0)
        ingresarDatos(idPasteleria)
        val latitud = findViewById<EditText>(R.id.input_latitud)
        val longitud = findViewById<EditText>(R.id.input_longitud)

        val botonCrearBDD = findViewById<Button>(R.id.btn_crear_bdd)
        botonCrearBDD
            .setOnClickListener {
                val nombrePasteleria = findViewById<EditText>(R.id.input_nombre_pastelerias)
                val nombreDueño = findViewById<EditText>(R.id.input_nom_dueño)
                val numEmpleados = findViewById<EditText>(R.id.input_num_Empleados)
                val ingresos = findViewById<EditText>(R.id.input_ingresos)
                BaseDeDatos.tablaPastelerias!!.crearPasteleria(
                    nombrePasteleria.text.toString(),
                    nombreDueño.text.toString(),
                    numEmpleados.text.toString().toInt(),
                    ingresos.text.toString().toDouble(),
                    latitud.text.toString().toDoubleOrNull(),
                    longitud.text.toString().toDoubleOrNull()
                )
                finish()
            }


        val botonActualizarBDD = findViewById<Button>(R.id.btn_actualizar_bdd)
        botonActualizarBDD
            .setOnClickListener {
                val id =  idPasteleria
                val nombrePasteleria = findViewById<EditText>(R.id.input_nombre_pastelerias)
                val nombreDueño = findViewById<EditText>(R.id.input_nom_dueño)
                val numEmpleados = findViewById<EditText>(R.id.input_num_Empleados)
                val ingresos = findViewById<EditText>(R.id.input_ingresos)
                BaseDeDatos.tablaPastelerias!!.actualizarPasteleria(
                    nombrePasteleria.text.toString(),
                    nombreDueño.text.toString(),
                    numEmpleados.text.toString().toInt(),
                    ingresos.text.toString().toDouble(),
                    latitud.text.toString().toDoubleOrNull(),
                    longitud.text.toString().toDoubleOrNull(),
                    id
                )
                finish()
            }

        //Establece que boton va a mostrarse en la pantalla
        if (idPasteleria !=0){
            botonCrearBDD
                .visibility = Button.INVISIBLE
        }else{
            botonActualizarBDD
                .visibility = Button.INVISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
    }

    fun ingresarDatos(idPasteleria: Int){
        if(idPasteleria != 0){
            val pasteleriaEncontrada = BaseDeDatos.tablaPastelerias!!.obtenerIDPasteleria(idPasteleria)
            val nombrePasteleria = findViewById<EditText>(R.id.input_nombre_pastelerias)
            val nombreDueño = findViewById<EditText>(R.id.input_nom_dueño)
            val numEmpleados = findViewById<EditText>(R.id.input_num_Empleados)
            val ingresos = findViewById<EditText>(R.id.input_ingresos)
            val latitud = findViewById<EditText>(R.id.input_latitud)
            val longitud = findViewById<EditText>(R.id.input_longitud)
            nombrePasteleria.setText(pasteleriaEncontrada.nombrePasteleria)
            nombreDueño.setText(pasteleriaEncontrada.nombreDueño)
            numEmpleados.setText(pasteleriaEncontrada.numEmpleados.toString())
            ingresos.setText(pasteleriaEncontrada.ingresos.toString())
            latitud.setText(pasteleriaEncontrada.latitud.toString())
            longitud.setText(pasteleriaEncontrada.longitud.toString())

        }
    }
}