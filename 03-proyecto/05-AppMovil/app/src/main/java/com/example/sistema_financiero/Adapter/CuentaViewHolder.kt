package com.example.sistema_financiero.Adapter

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sistema_financiero.Cuenta
import com.example.sistema_financiero.CuentaCRUD
import com.example.sistema_financiero.R

class CuentaViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    // Referencias a las vistas dentro del item layout
    val idCuenta = view.findViewById<TextView>(R.id.numero)
    val nomCuenta = view.findViewById<TextView>(R.id.nombre)
    val montoInicial = view.findViewById<TextView>(R.id.monto)

    // Botones en la vista
    val editar: ImageButton = itemView.findViewById(R.id.updateButton)
    val eliminar: ImageButton = itemView.findViewById(R.id.deleteButton)

    fun render(cuenta: Cuenta, onItemDeleteListener: ((Cuenta) -> Unit)?) {
        idCuenta.text = cuenta.id.toString()
        nomCuenta.text = cuenta.nombreCuenta
        montoInicial.text = cuenta.montoInicial.toString()

        editar.setOnClickListener {
            val context = itemView.context
            val intent = Intent(context, CuentaCRUD::class.java).apply {
                putExtra("cuentaSelecionada", cuenta)
            }
            context.startActivity(intent)
        }

        eliminar.setOnClickListener {
            val context = itemView.context
            AlertDialog.Builder(context).apply {
                setTitle("Confirmar eliminación")
                setMessage("¿Estás seguro de que deseas eliminar esta cuenta?")
                setPositiveButton("Eliminar") { dialog, _ ->
                    onItemDeleteListener?.invoke(cuenta)
                    dialog.dismiss()
                }
                setNegativeButton("Cancelar") { dialog, _ ->
                    dialog.dismiss()
                }
            }.show()
        }
    }
}