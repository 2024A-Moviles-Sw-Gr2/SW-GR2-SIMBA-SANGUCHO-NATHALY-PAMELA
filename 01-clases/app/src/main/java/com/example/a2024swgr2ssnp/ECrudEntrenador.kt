package com.example.a2024swgr2ssnp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar

class ECrudEntrenador : AppCompatActivity() {
    fun mostrarSnackbar(texto: String) {
        val snack = Snackbar.make(
            findViewById(R.id.id_layout_main),
            texto,
            Snackbar.LENGTH_INDEFINITE
        )
        snack.show()
    }
    override fun onCreate(savedInstanceState: Bundle?) { // sqlite
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ecrud_entrenador)
        //Buscar enrenador
        val botonBuscarBDD = findViewById<Button>(R.id.btn_buscar_bdd)
        botonBuscarBDD.setOnClickListener {
            val id=findViewById<EditText>(R.id.input_id)
            val nombre=findViewById<EditText>(R.id.input_nombre)
            val descripcion= findViewById<EditText>(R.id.input_descripcion)
            val entrenador = EBaseDeDatos.tablaEntrenador!!
                .consultarEntrenadorID(
                    id.text.toString().toInt()
                )
            if(entrenador==null){
                mostrarSnackbar("Usu no encontrado")
                id.setText("")
                nombre.setText("")
                descripcion.setText("")
            }else{
                id.setText(entrenador.id.toString())
                nombre.setText(entrenador.nombre)
                descripcion.setText(entrenador.descripcion)
                mostrarSnackbar("Usu. Encontrado")
            }

        }
        val botonCrearBDD=findViewById<Button>(R.id.btn_crear_bdd)
        botonCrearBDD.setOnClickListener {
            val nombre = findViewById<EditText>(R.id.input_nombre)
            val descripcion = findViewById<EditText>(R.id.input_descripcion)
            val respuesta = EBaseDeDatos.tablaEntrenador!!
                .crearEntrenador(
                    nombre.text.toString(),
                    descripcion.text.toString()
                )
            if(respuesta) mostrarSnackbar("Entr. creado")
        }
        val botonActualizaBB= findViewById<Button>(R.id.btn_actualizar_bdd)
        botonActualizaBB.setOnClickListener {
            val id = findViewById<EditText>(R.id.input_id)
            val nombre = findViewById<EditText>(R.id.input_nombre)
            val descripcion = findViewById<EditText>(R.id.input_descripcion)
            val respuesta = EBaseDeDatos.tablaEntrenador!!
                .actualizarEntrenadorFormulario(
                    nombre.text.toString(),
                    descripcion.text.toString(),
                    id.text.toString().toInt()
                )
            if(respuesta) mostrarSnackbar("Entr. Actualizado")
        }
        val botonEliminarBDD = findViewById<Button>(R.id.btn_eliminar_bdd)
        botonEliminarBDD.setOnClickListener {
            val id = findViewById<EditText>(R.id.input_id)
            val respuesta = EBaseDeDatos.tablaEntrenador!!
                .eliminarEntrenadorFormulario(
                    id.text.toString().toInt()
                )
            if (respuesta) mostrarSnackbar("Entr. Eliminado")
        }
    }
}