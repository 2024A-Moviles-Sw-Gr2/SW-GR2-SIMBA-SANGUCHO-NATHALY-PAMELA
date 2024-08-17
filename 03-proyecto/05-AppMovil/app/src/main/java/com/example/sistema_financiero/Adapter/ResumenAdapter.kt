package com.example.sistema_financiero.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sistema_financiero.Cuenta
import com.example.sistema_financiero.R

class ResumenAdapter(private var cuentas: MutableList<Cuenta>):RecyclerView.Adapter<ResumenViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResumenViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ResumenViewHolder(layoutInflater.inflate(R.layout.item_resumen_cuenta, parent, false))

    }

    override fun getItemCount(): Int = cuentas.size

    override fun onBindViewHolder(holder: ResumenViewHolder, position: Int) {
        val item = cuentas[position]
        holder.render(item)
    }
}