package com.example.sistema_financiero

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import java.text.NumberFormat
import java.util.Locale

class CuentaCRUD : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cuenta_crud)

        val cancelarButton: Button = findViewById(R.id.cancelarCrudCuenta)
        cancelarButton.setOnClickListener {
            finish()
        }

        val nombreCuenta = findViewById<EditText>(R.id.CrudCuenta_nombreCuenta)
        val montoEditText = findViewById<EditText>(R.id.CrudCuenta_monto)

        val cuentaSelecionada = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("cuentaSelecionada", Cuenta::class.java)
        } else {
            intent.getParcelableExtra<Cuenta>("cuentaSelecionada")
        }

        if (cuentaSelecionada == null) {
            // Crear nueva cuenta
            val btnCrearCuenta = findViewById<Button>(R.id.confirmarCrudCuenta)
            btnCrearCuenta.setOnClickListener {
                val monto = montoEditText.text.toString().replace(",", "").toDoubleOrNull() ?: 0.0
                val nuevaCuenta = BaseDeDatos.tablaCuentas!!.crearCuenta(
                    nombreCuenta.text.toString(),
                    monto
                )
                val resultIntent = Intent()
                resultIntent.putExtra("cuentaCreada", nuevaCuenta)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        } else {
            // Actualizar cuenta existente
            nombreCuenta.setText(cuentaSelecionada.nombreCuenta)
            val numberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault())
            montoEditText.setText(numberFormat.format(cuentaSelecionada.montoInicial).replace(numberFormat.currency.symbol, ""))

            val btnActualizarCuenta = findViewById<Button>(R.id.confirmarCrudCuenta)
            btnActualizarCuenta.setOnClickListener {
                val monto = montoEditText.text.toString().replace(",", "").toDoubleOrNull() ?: 0.0
                val cuentaActualizada = BaseDeDatos.tablaCuentas?.actualizarCuenta(
                    nombreCuenta.text.toString(),
                    monto,
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