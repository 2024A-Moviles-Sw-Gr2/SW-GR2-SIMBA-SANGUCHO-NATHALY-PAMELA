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
        private val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val pastelesArchivo = File("src/main/resources/pastel.txt")

        init {
            if (!pastelesArchivo.exists()) pastelesArchivo.createNewFile()
        }

        fun crearPastel(pastel: Pastel, nombrePasteleria: String) {
            val pastelerias = Pasteleria.leerPasteleria().toMutableList()
            val pasteleria = pastelerias.find { it.nombrePasteleria == nombrePasteleria }

            if (pasteleria != null) {
                val pasteles = leerPasteles().toMutableList()
                pasteles.add(pastel)
                guardarPasteles(pasteles)

                pasteleria.pasteles.add(pastel)
                Pasteleria.actualizarEnPasteles(nombrePasteleria, pasteleria.pasteles)
                println("Pastel creado exitosamente")
            } else {
                println("No existe la pastelería '$nombrePasteleria'.")
            }
        }

        fun leerPasteles(): List<Pastel> {
            return pastelesArchivo.readLines().mapNotNull { line ->
                val info = line.split(",").map { it.trim() }
                if (info.size == 6) {
                    try {
                        val nombre = info[0]
                        val nombrePasteleria = info[1]
                        val fechaFabricacion = Date(info[2].toLong())
                        val numPasteles = info[3].toInt()
                        val aptoDiabeticos = info[4].toBoolean()
                        val precio = info[5].replace(',', '.').toDouble()

                        Pastel(nombre, nombrePasteleria, fechaFabricacion, numPasteles, aptoDiabeticos, precio)
                    } catch (e: Exception) {
                        println("Ha ocurrido un error")
                        null
                    }
                } else {
                    println("Línea con formato incorrecto: $line")
                    null
                }
            }
        }

        fun guardarPasteles(pasteles: List<Pastel>) {
            pastelesArchivo.writeText("")
            pasteles.forEach { pastel ->
                val linea = "${pastel.nombre},${pastel.nombrePasteleria},${pastel.fechaFabricacion.time}," +
                        "${pastel.numPasteles},${pastel.aptoDiabeticos},${pastel.precio}  \n"
                pastelesArchivo.appendText(linea)
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