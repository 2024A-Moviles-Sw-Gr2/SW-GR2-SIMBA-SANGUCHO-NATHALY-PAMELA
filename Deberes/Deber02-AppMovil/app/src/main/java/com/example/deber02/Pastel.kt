package com.example.deber02

import java.text.SimpleDateFormat
import java.util.Date

class Pastel (
    var idPastel: Int?,
    var nombrePastel: String?,
    var precio: Double?,
    var fechaElab: Date?,
    var esParaDiabeticos: Boolean?,
    var idPasteleria: Int?
){
    override fun toString(): String {
        val formatoFecha = SimpleDateFormat("dd/MM/yyyy")
        val fecha = formatoFecha.format(fechaElab)
        return "${idPastel}, ${nombrePastel} , $ ${precio}, ${fecha}, ${esParaDiabeticos}"
    }
}