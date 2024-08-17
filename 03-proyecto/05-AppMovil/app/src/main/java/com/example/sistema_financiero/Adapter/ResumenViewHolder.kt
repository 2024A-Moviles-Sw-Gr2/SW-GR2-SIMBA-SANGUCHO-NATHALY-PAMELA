package com.example.sistema_financiero.Adapter

import android.content.Intent
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.sistema_financiero.Cuenta
import com.example.sistema_financiero.CuentaCRUD
import com.example.sistema_financiero.EgresoActivity
import com.example.sistema_financiero.IngresoActivity
import com.example.sistema_financiero.R



class ResumenViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    // Referencias a las vistas dentro del item layout
    val tipoCuenta = view.findViewById<TextView>(R.id.tvEfectivo)
    val valor = view.findViewById<TextView>(R.id.tvValor)
    val btnIngreso: ImageButton = view.findViewById(R.id.btnIngreso)
    val tvIngresoText: TextView = view.findViewById(R.id.textIngreso)
    val btnEgreso: ImageButton = view.findViewById(R.id.btnEgreso)
    val tvEgresoText: TextView = view.findViewById(R.id.textEgreso)
    val btnFavoritos: ImageButton = view.findViewById(R.id.btnFavorito)

    fun render(cuenta: Cuenta){
        tipoCuenta.text = cuenta.nombreCuenta
        valor.text = cuenta.montoInicial.toString()

        btnFavoritos.setOnClickListener {

        }
        btnIngreso.setOnClickListener {
            val context = itemView.context
            val intent = Intent(context, IngresoActivity::class.java)
            context.startActivity(intent)
        }
        tvIngresoText.text = "Ingresos"
        btnEgreso.setOnClickListener {
            val context = itemView.context
            val intent = Intent(context, EgresoActivity::class.java)
            context.startActivity(intent)
        }
        tvEgresoText.text = "Egresos"

    }
}