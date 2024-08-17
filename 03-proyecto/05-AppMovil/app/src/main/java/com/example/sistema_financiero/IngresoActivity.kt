package com.example.sistema_financiero

import android.app.Activity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.sistema_financiero.databinding.ActivityIngresoBinding
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*

class IngresoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIngresoBinding
    private lateinit var sqliteHelper: SqliteHelper
    private lateinit var montoTotalTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIngresoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        montoTotalTextView = findViewById(R.id.montoTotal)
        sqliteHelper = SqliteHelper(this)

        val cuentaId = intent.getIntExtra("CUENTA_ID", -1)

        if (cuentaId != -1) {
            val cuenta = sqliteHelper.obtenerIDCuenta(cuentaId) // Obtener la cuenta por ID
            cuenta?.let {
                montoTotalTextView.text = "$ ${cuenta.montoInicial}" // Muestra el monto actual
            }

            setupUI()
        }
    }

    private fun setupUI() {
        setupDatePicker()
        setupCategoryDropdown()
        setupAccountDropdown()
        setupButtons()
    }

    private fun setupDatePicker() {
        binding.fechaEditText.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.addOnPositiveButtonClickListener { selection ->
                val timeZone = TimeZone.getDefault()
                val offset = timeZone.getOffset(Date().time) * -1
                val correctedDate = Date(selection + offset)
                val formattedDate =
                    SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(correctedDate)
                binding.fechaEditText.setText(formattedDate)
            }
            datePicker.show(supportFragmentManager, "DATE_PICKER")
        }
    }

    private fun setupCategoryDropdown() {
        val categories = listOf("Universidad", "Comida", "Viaje", "Otros")
        val adapter = ArrayAdapter(this, R.layout.list_item, categories)
        binding.categoriaAutoComplete.setAdapter(adapter)
    }

    private fun setupAccountDropdown() {
        val accounts = sqliteHelper.obtenerCuentas() // Asegúrate de usar sqliteHelper aquí
        val accountNames = accounts.map { it.nombreCuenta }
        val adapter = ArrayAdapter(this, R.layout.list_item, accountNames)
        binding.cuentaDestinoAutoCompleteTextView.setAdapter(adapter)
    }

    private fun updateSaldoDisponible(saldo: Double) {
        binding.montoTotal.setText(String.format("%.2f", saldo))
    }

    private fun setupButtons() {
        binding.cancelarButton.setOnClickListener {
            finish()
        }

        binding.confirmarButton.setOnClickListener {
            val montoIngresado = binding.montoEditText.text.toString().toDoubleOrNull()
            if (montoIngresado == null || montoIngresado <= 0) {
                mostrarAlerta("Error", "Por favor, ingrese un monto válido mayor que cero.")
                return@setOnClickListener
            }

            val cuentaOrigenId = intent.getIntExtra("CUENTA_ID", -1)
            if (cuentaOrigenId == -1) {
                mostrarAlerta("Error", "No se pudo identificar la cuenta de origen.")
                return@setOnClickListener
            }

            val cuentaDestinoNombre = binding.cuentaDestinoAutoCompleteTextView.text.toString()
            val cuentaDestino =
                sqliteHelper.obtenerCuentas().find { it.nombreCuenta == cuentaDestinoNombre }
            if (cuentaDestino == null) {
                mostrarAlerta("Error", "No se pudo identificar la cuenta de destino.")
                return@setOnClickListener
            }

            val cuentaOrigen = sqliteHelper.obtenerIDCuenta(cuentaOrigenId)
            if (cuentaOrigen.montoInicial < montoIngresado) {
                mostrarAlerta(
                    "Saldo insuficiente",
                    "No tiene suficiente saldo en la cuenta de origen para realizar esta transferencia."
                )
                return@setOnClickListener
            }

            // Realizar la transferencia
            val nuevoMontoOrigen = cuentaOrigen.montoInicial - montoIngresado
            val nuevoMontoDestino = cuentaDestino.montoInicial + montoIngresado

            val cuentaOrigenActualizada = sqliteHelper.actualizarCuenta(
                cuentaOrigen.nombreCuenta,
                nuevoMontoOrigen,
                cuentaOrigenId
            )
            val cuentaDestinoActualizada = sqliteHelper.actualizarCuenta(
                cuentaDestino.nombreCuenta,
                nuevoMontoDestino,
                cuentaDestino.id
            )

            if (cuentaOrigenActualizada != null && cuentaDestinoActualizada != null) {
                // Transferencia exitosa
                mostrarAlerta(
                    "Transferencia realizada",
                    "Se ha transferido $$montoIngresado de ${cuentaOrigen.nombreCuenta} a ${cuentaDestino.nombreCuenta}."
                ) {
                    setResult(Activity.RESULT_OK)
                    finish()
                }
            } else {
                // Error en la transferencia
                mostrarAlerta("Error", "No se pudo realizar la transferencia. Intente nuevamente.")
            }
        }
    }

    private fun mostrarAlerta(
        titulo: String,
        mensaje: String,
        onPositiveClick: (() -> Unit)? = null
    ) {
        AlertDialog.Builder(this)
            .setTitle(titulo)
            .setMessage(mensaje)
            .setPositiveButton("OK") { _, _ -> onPositiveClick?.invoke() }
            .show()
    }
}