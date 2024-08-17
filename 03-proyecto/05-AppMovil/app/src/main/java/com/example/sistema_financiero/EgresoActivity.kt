package com.example.sistema_financiero

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.sistema_financiero.databinding.ActivityEgresoBinding
import com.example.sistema_financiero.databinding.ActivityIngresoBinding
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*

class EgresoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEgresoBinding
    private lateinit var sqliteHelper: SqliteHelper
    private lateinit var montoTotalTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEgresoBinding.inflate(layoutInflater)
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
        setupUI()
    }

    private fun setupUI() {
        setupDatePicker()
        setupCategoryDropdown()
        setupButtons()
    }

    private fun setupDatePicker() {
        binding.fechaEgreso.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.addOnPositiveButtonClickListener { selection ->
                val timeZone = TimeZone.getDefault()
                val offset = timeZone.getOffset(Date().time) * -1
                val correctedDate = Date(selection + offset)
                val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(correctedDate)
                binding.fechaEgreso.setText(formattedDate)
            }
            datePicker.show(supportFragmentManager, "DATE_PICKER")
        }
    }
    private fun setupCategoryDropdown() {
        val categories = listOf("Universidad", "Comida", "Viaje", "Otros")
        val adapter = ArrayAdapter(this, R.layout.list_item, categories)
        binding.categoriaAuto.setAdapter(adapter)
    }

    private fun setupButtons() {
        binding.cancelarEgreso.setOnClickListener {
            finish()
        }

        binding.confirmarEgreso.setOnClickListener {
        }
    }
}