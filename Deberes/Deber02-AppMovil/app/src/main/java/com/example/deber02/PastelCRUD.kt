package com.example.deber02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat

class PastelCRUD : AppCompatActivity() {
    val formatoFecha = SimpleDateFormat("dd/MM/yyyy")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pastel_crud)

        val idPastel = intent.getIntExtra("idPastel", 0)
        val idPasteleria = intent.getIntExtra("id", 0)
        ingresarDatos(idPastel)


        val botonCrearBDD = findViewById<Button>(R.id.btn_crear_pastel)
        botonCrearBDD
            .setOnClickListener {
                try {
                    val nombrePastel = findViewById<EditText>(R.id.input_nombre_pastel)
                    val precio = findViewById<EditText>(R.id.input_precio)
                    val fechaElab = findViewById<EditText>(R.id.input_fecha_elab)
                    val esParaDiabeticos: Boolean = findViewById<Switch>(R.id.sw_diabeticos).isChecked
                    val idPasteleria = idPasteleria
                    BaseDeDatos.tablaPasteles!!.crearPastel(
                        nombrePastel.text.toString(),
                        precio.text.toString().toDouble(),
                        fechaElab.text.toString(),
                        esParaDiabeticos,
                        idPasteleria
                    )
                    finish()
                } catch (e: Exception) {
                    mostrarSnackBar("Datos Ingresados Incorrectos")
                }
            }

        val botonActualizarBDD = findViewById<Button>(R.id.btn_act_pastel)
        botonActualizarBDD
            .setOnClickListener {
                try {
                    val id = idPastel
                    val nombrePastel = findViewById<EditText>(R.id.input_nombre_pastel)
                    val precio = findViewById<EditText>(R.id.input_precio)
                    val fechaElab = findViewById<EditText>(R.id.input_fecha_elab)
                    val esParaDiabeticos: Boolean = findViewById<Switch>(R.id.sw_diabeticos).isChecked
                    val idPasteleria = idPasteleria
                    BaseDeDatos.tablaPasteles!!.actualizarPastel(
                        nombrePastel.text.toString(),
                        precio.text.toString().toDouble(),
                        fechaElab.text.toString(),
                        esParaDiabeticos,
                        idPasteleria,
                        id
                    )
                    finish()
                } catch (e: Exception) {
                    mostrarSnackBar("Datos Ingresados Incorrectos")
                }
            }
        if (idPastel !=0){
            botonCrearBDD
                .visibility = Button.INVISIBLE
        }else{
            botonActualizarBDD
                .visibility = Button.INVISIBLE
        }
    }

    fun ingresarDatos(idPastel: Int) {
        if (idPastel != 0) {
            val pastelEncontrado =
                BaseDeDatos.tablaPasteles!!.obtenerIDPastel(idPastel)
            if (pastelEncontrado != null) {
                findViewById<EditText>(R.id.input_nombre_pastel).setText(pastelEncontrado.nombrePastel)
                findViewById<EditText>(R.id.input_precio).setText(pastelEncontrado.precio.toString())
                findViewById<EditText>(R.id.input_fecha_elab).setText(
                    formatoFecha.format(
                        pastelEncontrado.fechaElab
                    )
                )
                findViewById<Switch>(R.id.sw_diabeticos).isChecked = pastelEncontrado.esParaDiabeticos!!
            }
        }
    }

    fun mostrarSnackBar(texto: String) {
        Snackbar.make(
            findViewById(R.id.layout_crud_pasteles),
            texto,
            Snackbar.LENGTH_LONG
        )
            .setAction("Action", null).show()
    }
}