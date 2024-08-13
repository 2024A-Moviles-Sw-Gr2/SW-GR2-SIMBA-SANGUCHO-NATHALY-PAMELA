package com.example.deber02

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.text.SimpleDateFormat

class SqliteHelper(
    contexto: Context?, // this
) : SQLiteOpenHelper(
    contexto,
    "Deber02", // nombre bdd
    null,
    5
) {
    val formatoFecha = SimpleDateFormat("dd/MM/yyyy")

    override fun onCreate(db: SQLiteDatabase?) {
        val crearTablaPasteleria =
            """
                CREATE TABLE PASTELERIA(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nombrePasteleria VARCHAR(50),
                    nombreDueño VARCHAR(50),
                    numEmpleados INTEGER,
                    ingresos DOUBLE
                )
            """.trimIndent()
        db?.execSQL(crearTablaPasteleria)

        val crearTablaPastel =
            """
                CREATE TABLE PASTEL(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nombrePastel VARCHAR(50),
                    precio DOUBLE,
                    fechaElab DATE,
                    esParaDiabeticos BOOLEAN,
                    idPasteleria INTEGER,
                    FOREIGN KEY(idPasteleria) REFERENCES PASTELERIA(id)
                )
            """.trimIndent()
        db?.execSQL(crearTablaPastel)
    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {
    }

    /*
   ********* CRUD PASTELERIA *********
     */
    fun crearPasteleria(
        nombrePasteleria: String,
        nombreDueño: String,
        numEmpleados: Int,
        ingresos: Double

    ): Boolean {
        val baseDatosEscritura = writableDatabase
        val datosAGuardar = ContentValues()
        datosAGuardar.put("nombrePasteleria", nombrePasteleria)
        datosAGuardar.put("nombreDueño", nombreDueño)
        datosAGuardar.put("numEmpleados", numEmpleados)
        datosAGuardar.put("ingresos", ingresos)
        val resultadoGuardar = baseDatosEscritura
            .insert(
                "PASTELERIA", // nombre tabla
                null,
                datosAGuardar, // valores
            )
        baseDatosEscritura.close()
        return if (resultadoGuardar.toInt() === -1) false else true
    }

    fun eliminarPasteleria(id: Int): Boolean {
        val conexionEscritura = writableDatabase
        val parametrosConsultaDelete = arrayOf(id.toString())
        val resultadoEliminacion = conexionEscritura
            .delete(
                "PASTELERIA", // Nombre tabla
                "id=?", // Consulta Where
                parametrosConsultaDelete
            )
        conexionEscritura.close()
        return if (resultadoEliminacion.toInt() == -1) false else true
    }


    fun actualizarPasteleria(
        nombrePasteleria: String,
        nombreDueño: String,
        numEmpleados: Int,
        ingresos: Double,
        id: Int,
    ): Boolean {
        val conexionEscritura = writableDatabase
        val datosAActualizar = ContentValues()
        datosAActualizar.put("nombrePasteleria", nombrePasteleria)
        datosAActualizar.put("nombreDueño", nombreDueño)
        datosAActualizar.put("numEmpleados", numEmpleados)
        datosAActualizar.put("ingresos", ingresos)
        // where ID = ?
        val parametrosConsultaActualizar = arrayOf(id.toString())
        val resultadoActualizacion = conexionEscritura
            .update(
                "PASTELERIA", // Nombre tabla
                datosAActualizar, // Valores
                "id=?", // Consulta Where
                parametrosConsultaActualizar
            )
        conexionEscritura.close()
        return if (resultadoActualizacion.toInt() == -1) false else true
    }


    fun obtenerIDPasteleria(id: Int): Pasteleria {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM PASTELERIA WHERE ID = ?
        """.trimIndent()
        val parametrosConsultaLectura = arrayOf(id.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura,
            parametrosConsultaLectura,
        )
        val existePasteleria = resultadoConsultaLectura.moveToFirst()
        val PasteleriaEncontrada = Pasteleria(0, "", "", 0, 0.0)
        val arreglo = arrayListOf<Pasteleria>()
        if (existePasteleria) {
            do {
                val id = resultadoConsultaLectura.getInt(0) // Indice 0
                val nombrePasteleria = resultadoConsultaLectura.getString(1)
                val nombreDueño = resultadoConsultaLectura.getString(2)
                val numEmpleados = resultadoConsultaLectura.getInt(3)
                val ingresos = resultadoConsultaLectura.getDouble(4)
                if (id != null) {
                    PasteleriaEncontrada.id = id
                    PasteleriaEncontrada.nombrePasteleria = nombrePasteleria
                    PasteleriaEncontrada.nombreDueño = nombreDueño
                    PasteleriaEncontrada.numEmpleados = numEmpleados
                    PasteleriaEncontrada.ingresos = ingresos
                }
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return PasteleriaEncontrada
    }

    fun obtenerPastelerias(): ArrayList<Pasteleria> {
        val scriptConsultarPasteleria = "SELECT * FROM PASTELERIA"
        val baseDatosLectura = readableDatabase
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultarPasteleria,
            null
        )
        val existePasteleria = resultadoConsultaLectura.moveToFirst()
        val arrayPastelerias = arrayListOf<Pasteleria>()
        if (existePasteleria) {
            do {
                val id = resultadoConsultaLectura.getInt(0)
                val nombrePasteleria = resultadoConsultaLectura.getString(1)
                val nombreDueño = resultadoConsultaLectura.getString(2)
                val numEmpleados = resultadoConsultaLectura.getInt(3)
                val ingresos = resultadoConsultaLectura.getDouble(4)
                arrayPastelerias.add(
                    Pasteleria(
                        id,
                        nombrePasteleria,
                        nombreDueño,
                        numEmpleados,
                        ingresos
                    )
                )
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return arrayPastelerias
    }

    /*
   ********* CRUD PASTEL *********
     */

    fun crearPastel(
        nombrePastel: String,
        precio: Double,
        fechaElab: String,
        esParaDiabeticos: Boolean,
        idPasteleria: Int,

        ): Boolean {
        val baseDatosEscritura = writableDatabase
        val datosAGuardar = ContentValues()
        datosAGuardar.put("nombrePastel", nombrePastel)
        datosAGuardar.put("precio", precio)
        datosAGuardar.put("fechaElab", fechaElab)
        datosAGuardar.put("esParaDiabeticos", esParaDiabeticos)
        datosAGuardar.put("idPasteleria", idPasteleria)
        val resultadoGuardar = baseDatosEscritura
            .insert(
                "PASTEL", // nombre tabla
                null,
                datosAGuardar, // valores
            )
        baseDatosEscritura.close()
        return if (resultadoGuardar.toInt() === -1) false else true
    }

    fun eliminarPastel(id: Int): Boolean {
        val conexionEscritura = writableDatabase
        // where ID = ?
        val parametrosConsultaDelete = arrayOf(id.toString())
        val resultadoEliminacion = conexionEscritura
            .delete(
                "PASTEL",
                "id=?",
                parametrosConsultaDelete
            )
        conexionEscritura.close()
        return if (resultadoEliminacion.toInt() == -1) false else true
    }


    fun actualizarPastel(
        nombrePastel: String,
        precio: Double,
        fechaElab: String,
        esParaDiabeticos: Boolean,
        idPasteleria: Int,
        id: Int,
    ): Boolean {
        val conexionEscritura = writableDatabase
        val datosAActualizar = ContentValues()
        datosAActualizar.put("nombrePastel", nombrePastel)
        datosAActualizar.put("precio", precio)
        datosAActualizar.put("fechaElab", fechaElab)
        datosAActualizar.put("esParaDiabeticos", esParaDiabeticos)
        datosAActualizar.put("idPasteleria", idPasteleria)
        // where ID = ?
        val parametrosConsultaActualizar = arrayOf(id.toString())
        val resultadoActualizacion = conexionEscritura
            .update(
                "PASTEL",
                datosAActualizar,
                "id=?",
                parametrosConsultaActualizar
            )
        conexionEscritura.close()
        return if (resultadoActualizacion.toInt() == -1) false else true
    }


    fun obtenerIDPastel(id: Int): Pastel {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM PASTEL WHERE ID = ?
        """.trimIndent()
        val parametrosConsultaLectura = arrayOf(id.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura,
            parametrosConsultaLectura,
        )
        val existePastel = resultadoConsultaLectura.moveToFirst()
        val pastelEncontrado = Pastel(0, "", 0.0, null, false, 0)
        if (existePastel) {
            do {
                val id = resultadoConsultaLectura.getInt(0) // Indice 0
                val nombrePastel = resultadoConsultaLectura.getString(1)
                val precio = resultadoConsultaLectura.getDouble(2)
                val fechaElab = resultadoConsultaLectura.getString(3)
                val esParaDiabeticos = resultadoConsultaLectura.getInt(4)
                val idPasteleria = resultadoConsultaLectura.getInt(5)
                if (id != null) {
                    pastelEncontrado.idPastel = id
                    pastelEncontrado.nombrePastel = nombrePastel
                    pastelEncontrado.precio = precio
                    pastelEncontrado.fechaElab = formatoFecha.parse(fechaElab)
                    if (esParaDiabeticos == 1) {
                        pastelEncontrado.esParaDiabeticos = true
                    }
                }
                pastelEncontrado.idPasteleria = idPasteleria
            } while (resultadoConsultaLectura.moveToNext())
        }

        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return pastelEncontrado

    }

    fun obtenerPastelesDePastelerias(idPasteleria: Int): ArrayList<Pastel> {
        val scriptConsultarLugares = "SELECT * FROM PASTEL WHERE idPasteleria = ?"
        val baseDatosLectura = readableDatabase
        val parametrosConsultaLectura = arrayOf(idPasteleria.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultarLugares,
            parametrosConsultaLectura
        )
        val existePastel = resultadoConsultaLectura.moveToFirst()
        val arrayPasteles = arrayListOf<Pastel>()
        if (existePastel) {
            do {
                val id = resultadoConsultaLectura.getInt(0) // Indice 0
                val nombrePastel = resultadoConsultaLectura.getString(1)
                val precio = resultadoConsultaLectura.getDouble(2)
                val fechaElab = resultadoConsultaLectura.getString(3)
                val esParaDiabeticos = resultadoConsultaLectura.getInt(4)
                val idPasteleria = resultadoConsultaLectura.getInt(5)
                if (id != null) {
                    val pastelEncontrado = Pastel(0, "", 0.0, null, false, 0)
                    pastelEncontrado.idPastel = id
                    pastelEncontrado.nombrePastel = nombrePastel
                    pastelEncontrado.precio = precio
                    pastelEncontrado.fechaElab = formatoFecha.parse(fechaElab)
                    if (esParaDiabeticos == 1) {
                        pastelEncontrado.esParaDiabeticos = true
                    }
                    pastelEncontrado.idPasteleria = idPasteleria
                    arrayPasteles.add(pastelEncontrado)
                }
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return arrayPasteles
    }
}