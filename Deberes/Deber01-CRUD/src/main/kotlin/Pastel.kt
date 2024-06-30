import Pasteleria.Companion.actualizarEnPasteles
import Pasteleria.Companion.leerPasteleria
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

data class Pastel(
    var nombre: String,
    var nombrePasteleria: String,
    var fechaFabricacion: Date,
    var numPasteles: Int,
    var aptoDiabeticos: Boolean,
    var precio: Double
) {
    companion object {
        private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val pastelesArchivo = File("src/main/resources/pastel.txt")

        init {
            if (!pastelesArchivo.exists()) pastelesArchivo.createNewFile()
        }

        fun leerPasteles(): List<Pastel> {
            return pastelesArchivo.readLines().mapNotNull { line ->
                val info = line.split(",")
                if (info.size == 6) {
                    try {
                        Pastel(
                            info[0],
                            info[1],
                            Date(info[2].toLong()),
                            info[3].toInt(),
                            info[4].toBoolean(),
                            info[5].replace(',', '.').toDouble()
                        )
                    } catch (e: Exception) {
                        println("Ha ocurrido un error al ingresar los datos: $line. Error: ${e.message}")
                        null
                    }
                } else {
                    println("Línea en blanco: $line")
                    null
                }
            }
        }

        fun crearPastel(pastel: Pastel, nombrePasteleria: String) {
            val pastelerias = leerPasteleria().toMutableList()
            val pasteleria = pastelerias.find { it.nombrePasteleria == nombrePasteleria }

            if (pasteleria != null) {
                val linea = "${pastel.nombre},${pastel.nombrePasteleria},${pastel.fechaFabricacion.time}," +
                        "${pastel.numPasteles},${pastel.aptoDiabeticos},${pastel.precio}\n"
                pastelesArchivo.appendText(linea)

                pasteleria.pasteles.add(pastel)
                actualizarEnPasteles(nombrePasteleria, pasteleria.pasteles)
                println("Pastel creado exitosamente")
            } else {
                println("No existe la pasteleria '$nombrePasteleria'.")
            }
        }

        fun actualizarPastel(nombrePastel: String, nuevoNombrePastel: Pastel) {
            val pasteles = leerPasteles().toMutableList()
            val index = pasteles.indexOfFirst { it.nombre == nombrePastel }
            if (index != -1) {
                pasteles[index] = nuevoNombrePastel
                pastelesArchivo.bufferedWriter().use { writer ->
                    pasteles.forEach { pastel ->
                        writer.write("${pastel.nombre},${pastel.nombrePasteleria},${pastel.fechaFabricacion.time}," +
                                "${pastel.numPasteles},${pastel.aptoDiabeticos},${pastel.precio}\n")
                    }
                }
            } else {
                println("No se encontró el pastel con nombre '$nombrePastel'.")
            }
        }

        fun eliminarPastel(nombre: String) {
            val pasteles = leerPasteles()
            val pastelExiste = pasteles.any { it.nombre == nombre }

            if (pastelExiste) {
                val pastelesActualizados = pasteles.filterNot { it.nombre == nombre }
                pastelesArchivo.writeText(pastelesActualizados.joinToString("\n") {
                    "${it.nombre},${it.nombrePasteleria},${it.fechaFabricacion.time}," +
                            "${it.numPasteles},${it.aptoDiabeticos},${it.precio}"
                })

                println("¡Pastel '$nombre' eliminado exitosamente!")
            } else {
                println(" El pastel '$nombre' no existe.")
            }
        }

        fun actualizarNombrePasteleria(pasteles: MutableList<Pastel>, nombreViejo: String, nombreNuevo: String) {
            for (pastel in pasteles) {
                if (pastel.nombrePasteleria == nombreViejo) {
                    pastel.nombrePasteleria = nombreNuevo
                    val nuevoPastel = pastel.copy(nombrePasteleria = nombreNuevo)
                    actualizarPastel(pastel.nombre, nuevoPastel)
                }
            }
        }

    }
    override fun toString(): String {
        return "$nombre, Pastelería: $nombrePasteleria, " +
                "Fecha de Fabricación: ${dateFormat.format(fechaFabricacion)}, " +
                "Num. de Pasteles: $numPasteles, Apto para Diabéticos: $aptoDiabeticos, " +
                "Precio: $precio"
    }

}