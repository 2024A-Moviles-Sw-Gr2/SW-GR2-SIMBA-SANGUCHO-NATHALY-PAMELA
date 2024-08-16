package com.example.sistema_financiero

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.text.SimpleDateFormat

class SqliteHelper(
    contexto: Context?, // this
) : SQLiteOpenHelper(
    contexto,
    "ProyectoIIB", // nombre bdd
    null,
    5
) {
    val formatoFecha = SimpleDateFormat("dd/MM/yyyy")

    override fun onCreate(db: SQLiteDatabase?) {
        val crearTablaCuentas =  """
                CREATE TABLE CUENTA(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nombreCuenta VARCHAR(50),
                    montoInicial DOUBLE
                )
            """.trimIndent()
        db?.execSQL(crearTablaCuentas)
    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {
    }
    /*
    ********** CRUD CUENTA ***********
    * */
    fun crearCuenta(
        nombreCuenta: String,
        montoInicial: Double
    ): Boolean {
        val baseDatosEscritura = writableDatabase
        val datosAGuardar = ContentValues()
        datosAGuardar.put("nombreCuenta", nombreCuenta)
        datosAGuardar.put("montoInicial", montoInicial)
        val resultadoGuardar = baseDatosEscritura
            .insert(
                "CUENTA", // nombre tabla
                null,
                datosAGuardar, // valores
            )
        baseDatosEscritura.close()
        return if (resultadoGuardar.toInt() === -1) false else true
    }

    fun eliminarCuenta(id: Int): Boolean {
        val conexionEscritura = writableDatabase
        val parametrosConsultaDelete = arrayOf(id.toString())
        val resultadoEliminacion = conexionEscritura
            .delete(
                "CUENTA", // Nombre tabla
                "id=?", // Consulta Where
                parametrosConsultaDelete
            )
        conexionEscritura.close()
        return if (resultadoEliminacion.toInt() == -1) false else true
    }

    fun actualizarCuenta(
        nombreCuenta: String,
        montoInicial: Double,
        id: Int,
    ): Boolean {
        val conexionEscritura = writableDatabase
        val datosAActualizar = ContentValues()
        datosAActualizar.put("nombreCuenta", nombreCuenta)
        datosAActualizar.put("montoInicial", montoInicial)
        val parametrosConsultaActualizar = arrayOf(id.toString())
        val resultadoActualizacion = conexionEscritura
            .update(
                "CUENTA", // Nombre tabla
                datosAActualizar, // Valores
                "id=?", // Consulta Where
                parametrosConsultaActualizar
            )
        conexionEscritura.close()
        return if (resultadoActualizacion.toInt() == -1) false else true
    }
    fun obtenerCuentas(): ArrayList<Cuenta> {
        val scriptConsultarPasteleria = "SELECT * FROM CUENTA"
        val baseDatosLectura = readableDatabase
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultarPasteleria,
            null
        )
        val existeCuenta = resultadoConsultaLectura.moveToFirst()
        val arrayCuentas = arrayListOf<Cuenta>()
        if (existeCuenta) {
            do {
                val id = resultadoConsultaLectura.getInt(0)
                val nombreCuenta = resultadoConsultaLectura.getString(1)
                val ingresos = resultadoConsultaLectura.getDouble(2)
                arrayCuentas.add(
                    Cuenta(
                        id,
                        nombreCuenta,
                        ingresos
                    )
                )
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return arrayCuentas
    }
}