package com.example.sistema_financiero

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment

class CuentaCRUD : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cuenta_crud)

        val cancelarButton: Button = findViewById(R.id.cancelarCrudCuenta)
        cancelarButton.setOnClickListener {
            finish()
        }

        val nombreCuenta = findViewById<EditText>(R.id.CrudCuenta_nombreCuenta)
        val montoTextView = findViewById<TextView>(R.id.CrudCuenta_monto)

        val cuentaSelecionada = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("cuentaSelecionada", Cuenta::class.java)
        } else {
            intent.getParcelableExtra<Cuenta>("cuentaSelecionada")
        }

        if (cuentaSelecionada == null) {
            val btnCrearCuenta = findViewById<Button>(R.id.confirmarCrudCuenta)
            btnCrearCuenta.setOnClickListener {
                val nuevaCuenta = BaseDeDatos.tablaCuentas!!.crearCuenta(
                    nombreCuenta.text.toString(),
                    montoTextView.text.toString().toDoubleOrNull() ?: 0.0
                )
                val resultIntent = Intent()
                resultIntent.putExtra("cuentaCreada", nuevaCuenta)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        } else {
            nombreCuenta.setText(cuentaSelecionada.nombreCuenta)
            montoTextView.text = String.format("$ %.2f", cuentaSelecionada.montoInicial ?: 0.0)
            val btnActualizarCuenta = findViewById<Button>(R.id.confirmarCrudCuenta)
            btnActualizarCuenta.setOnClickListener {
                val cuentaActualizada = BaseDeDatos.tablaCuentas?.actualizarCuenta(
                    nombreCuenta.text.toString(),
                    cuentaSelecionada.montoInicial ?: 0.0,
                    cuentaSelecionada.id
                )
                val resultIntent = Intent()
                resultIntent.putExtra("cuentaActualizada", cuentaActualizada)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }
    }
}
