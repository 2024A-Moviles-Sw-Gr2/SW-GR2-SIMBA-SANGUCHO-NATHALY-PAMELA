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
        val crearTablaIngresos = """
            CREATE TABLE INGRESO(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                fechaIngreso TEXT,
                montoIngreso DOUBLE,
                cuentaOrigen VARCHAR(50),
                cuentaDestino VARCHAR(50),
                FOREIGN KEY(cuentaDestino) REFERENCES CUENTA(nombreCuenta)
            )
        """.trimIndent()
        db?.execSQL(crearTablaIngresos)
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
    ): Cuenta? {
        val baseDatosEscritura = writableDatabase
        val datosAGuardar = ContentValues()
        datosAGuardar.put("nombreCuenta", nombreCuenta)
        datosAGuardar.put("montoInicial", montoInicial)
        val resultadoGuardar = baseDatosEscritura
            .insert(
                "CUENTA",
                null,
                datosAGuardar,
            )
        baseDatosEscritura.close()
        return if (resultadoGuardar.toInt() == -1) null else Cuenta(resultadoGuardar.toInt(), nombreCuenta, montoInicial)
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
        id: Int
    ): Cuenta? {
        val conexionEscritura = writableDatabase
        val datosAActualizar = ContentValues()
        datosAActualizar.put("nombreCuenta", nombreCuenta)
        datosAActualizar.put("montoInicial", montoInicial)
        val parametrosConsultaActualizar = arrayOf(id.toString())
        val resultadoActualizacion = conexionEscritura.update(
            "CUENTA",
            datosAActualizar,
            "id=?",
            parametrosConsultaActualizar
        )
        conexionEscritura.close()
        return if (resultadoActualizacion > 0) Cuenta(id, nombreCuenta, montoInicial) else null
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
    fun obtenerIDCuenta(id: Int): Cuenta {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM CUENTA WHERE ID = ?
        """.trimIndent()
        val parametrosConsultaLectura = arrayOf(id.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura,
            parametrosConsultaLectura,
        )
        val existeCuenta = resultadoConsultaLectura.moveToFirst()
        val CuentaEncontrada = Cuenta(0, "", 0.0)
        val arreglo = arrayListOf<Cuenta>()
        if (existeCuenta) {
            do {
                val id = resultadoConsultaLectura.getInt(0) // Indice 0
                val nombreCuenta= resultadoConsultaLectura.getString(1)
                val montoInicial= resultadoConsultaLectura.getDouble(2)
                if (id != null) {
                    CuentaEncontrada.id = id
                    CuentaEncontrada.nombreCuenta=nombreCuenta
                    CuentaEncontrada.montoInicial=montoInicial
                }
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return CuentaEncontrada
    }


}