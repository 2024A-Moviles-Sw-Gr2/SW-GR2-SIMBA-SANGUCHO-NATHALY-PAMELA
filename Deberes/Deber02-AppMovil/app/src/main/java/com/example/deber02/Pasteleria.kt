package com.example.deber02

class Pasteleria(
    var id: Int?,
    var nombrePasteleria: String?,
    var nombreDueño: String?,
    var numEmpleados: Int?,
    var ingresos: Double?,
) {
    override fun toString(): String {
        return "${id},${nombrePasteleria}, ${nombreDueño}, ${numEmpleados}, ${ingresos}"
    }
}