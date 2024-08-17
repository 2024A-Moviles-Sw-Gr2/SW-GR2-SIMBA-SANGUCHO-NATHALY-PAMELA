package com.example.a2024swgr2ssnp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ESqliteHelperEntrenador(
    contexto : Context?
): SQLiteOpenHelper(
    contexto,
    "moviles",
    null,
    1
        ){
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaEntrenador=
            """
                CREATE TABLE ENTRENADOR(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre VARCHAR(50),
                descripcion VARCHAR(50)
                )
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaEntrenador)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1:Int, p2:Int) {
    }
    fun crearEntrenador(
        nombre: String,
        descripcion:String
    ):Boolean{
        val basedatosEscritura= writableDatabase
        val valoresAguardar = ContentValues()
        valoresAguardar.put("nombre",nombre)
        valoresAguardar.put("descripcion",descripcion)
        val resultadoGuardar= basedatosEscritura
            .insert(
                "ENTRENADOR", //nombre de la tabla
                null,
                    valoresAguardar //valores
            )
        basedatosEscritura.close()
        return if(resultadoGuardar.toInt()==-1)false else true
    }
    fun eliminarEntrenadorFormulario(id: Int):Boolean{
        var conexionEscritura= writableDatabase
        ///Consulta SQL: where... ID=? AND NOMBRE?
        val parametrosConsultaDelete = arrayOf(id.toString())
        val resultadEliminacion = conexionEscritura
            .delete(
                "ENTRENADOR",
                "id=?",
                parametrosConsultaDelete
            )
        conexionEscritura.close()
        return if(resultadEliminacion.toInt()==-1)false else true
    }
    fun actualizarEntrenadorFormulario(
        nombre: String,descripcion: String,id: Int
    ):Boolean{
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre",nombre)
        valoresAActualizar.put("descripcion",descripcion)
        //where
        val parametrosConsultaActualizar = arrayOf(id.toString())
        val resultadoAActualizar = conexionEscritura
            .update(
                "ENTRENADOR",
                valoresAActualizar, // nombre = Adrian, descripcion
                "id=?", // id = 1
                parametrosConsultaActualizar // (1)
            )
        conexionEscritura.close()
        return if (resultadoAActualizar.toInt()==-1)false else true
    }
    fun consultarEntrenadorID(id: Int):BEntrenador?{
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM ENTRENADOR WHERE ID =?
        """.trimIndent()
        val arregloParametrosConsultaLectura = arrayOf(
            id.toString()
        )
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura,
            arregloParametrosConsultaLectura
        )
        //Logica de busqueda
        //Se esta recibiendo un arreglo que puede ser null
        //Lo que vamos a hacer es llenar un arreglo de entrenadores
        //Si esta vacio el arreglo no tiene elementos

        val existeAlMenos = resultadoConsultaLectura.moveToFirst()
        val arregloRespuesta = arrayListOf<BEntrenador>()
        if (existeAlMenos){
            do{
                val entrenador = BEntrenador(
                    resultadoConsultaLectura.getInt(0),
                    resultadoConsultaLectura.getString(1),
                    resultadoConsultaLectura.getString(2)
                )
                arregloRespuesta.add(entrenador)
            }while(resultadoConsultaLectura.moveToNext())

        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        //ESqliteHelperEntrenador.consultarEntrenadorPorID
        return if(arregloRespuesta.size>0)arregloRespuesta[0]else null
    }
}