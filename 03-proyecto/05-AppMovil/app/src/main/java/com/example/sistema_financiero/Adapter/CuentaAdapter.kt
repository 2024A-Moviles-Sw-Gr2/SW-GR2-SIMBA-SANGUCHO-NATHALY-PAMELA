package com.example.sistema_financiero.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sistema_financiero.Cuenta
import com.example.sistema_financiero.R

class CuentaAdapter(private var cuentas: MutableList<Cuenta>) : RecyclerView.Adapter<CuentaViewHolder>() {

    private var onItemEditListener: ((Cuenta) -> Unit)? = null
    private var onItemDeleteListener: ((Cuenta) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CuentaViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CuentaViewHolder(layoutInflater.inflate(R.layout.item_cuenta, parent, false))
    }

    override fun getItemCount(): Int = cuentas.size

    override fun onBindViewHolder(holder: CuentaViewHolder, position: Int) {
        val item = cuentas[position]
        holder.render(item,onItemDeleteListener)

        holder.editar.setOnClickListener {
            onItemEditListener?.invoke(item)
        }
    }
    fun setOnItemEditListener(listener: (Cuenta) -> Unit) {
        this.onItemEditListener = listener
    }
    fun setOnItemDeleteListener(listener: (Cuenta) -> Unit) {
        this.onItemDeleteListener = listener
    }
    fun actualizarCuenta(cuentaActualizada: Cuenta) {
        val index = cuentas.indexOfFirst { it.id == cuentaActualizada.id }
        if (index != -1) {
            cuentas[index] = cuentaActualizada
            notifyItemChanged(index)
        }
    }

    fun eliminarCuenta(cuentaEliminada: Cuenta) {
        val index = cuentas.indexOfFirst { it.id == cuentaEliminada.id }
        if (index != -1) {
            cuentas.removeAt(index)
            notifyItemRemoved(index)
        }
    }
}
