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
        private val pastelesArchivo = File("src/main/resources/pastel.txt")

        init {
            if (!pastelesArchivo.exists()) pastelesArchivo.createNewFile()
        }

        fun ingresarDatosPastel(): Pastel {
            println("Ingrese el nombre del pastel:")
            val nombre = readLine().orEmpty()

            println("Ingrese nombre de la pasteleria:")
            val nombrePasteleria = readLine().orEmpty()

            val fechaFabricacion: Date
            while (true) {
                println("Ingrese la fecha de fabricacion (yyyy-MM-dd):")
                val fechaInput = readLine().orEmpty()
                fechaFabricacion = try {
                    dateFormat.parse(fechaInput)
                } catch (e: Exception) {
                    println("Ingreso un formato incorrecto")
                    continue
                }
                break
            }

            val numPasteles: Int
            while (true) {
                println("Cantidad de pasteles hechos:")
                val numPastelesInput = readLine().orEmpty()
                numPasteles = try {
                    numPastelesInput.toInt()
                } catch (e: Exception) {
                    println("Por favor, ingrese un numero entero")
                    continue
                }
                break
            }

            val aptoDiabeticos: Boolean
            while (true) {
                println("¿Es apto para diabéticos? (si/no):")
                val aptoDiabeticosInput = readLine().orEmpty().toLowerCase()
                aptoDiabeticos = when (aptoDiabeticosInput) {
                    "si" -> true
                    "no" -> false
                    else -> {
                        println("Por favor, ingrese 'si' o 'no'.")
                        continue
                    }
                }
                break
            }

            val precio: Double
            while (true) {
                println("Ingrese el precio:")
                val precioInput = readLine()
                if (precioInput != null && precioInput.matches(Regex("-?\\d+(\\.\\d+)?"))) {
                    precio = precioInput.toDouble()
                    break
                } else {
                    println("Por favor, ingrese un numero decimal")
                }
            }

            return Pastel(nombre, nombrePasteleria, fechaFabricacion, numPasteles, aptoDiabeticos, precio)
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
            val pastelerias = Pasteleria.leerPasteleria().toMutableList()
            val pasteleria = pastelerias.find { it.nombrePasteleria == nombrePasteleria }

            if (pasteleria != null) {
                pastelesArchivo.appendText("${pastel.nombre},${pastel.nombrePasteleria},${pastel.fechaFabricacion.time}," +
                        "${pastel.numPasteles},${pastel.aptoDiabeticos},${pastel.precio}\n")

                pasteleria.pasteles.add(pastel)
                Pasteleria.actualizarEnPasteles(nombrePasteleria, pasteleria.pasteles)
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
        fun eliminarPastel(nombrePastel: String) {
            val pasteles = leerPasteles().filterNot { it.nombre == nombrePastel }
            pastelesArchivo.writeText(pasteles.joinToString("\n") {
                "${it.nombre},${it.nombrePasteleria},${it.fechaFabricacion.time}," +
                        "${it.numPasteles},${it.aptoDiabeticos},${it.precio}"
            })
        }

        fun modificarPorNombre(): String {
            println("Ingrese nombre del pastel:")
            return readLine().orEmpty()
        }

        fun actualizarNombrePasteleria(pastelerias: MutableList<Pasteleria>, nombreViejo: String, nombreNuevo: String) {
            val pasteleriaActualizada = pastelerias.find { it.nombrePasteleria == nombreViejo }
            if (pasteleriaActualizada != null) {
                pasteleriaActualizada.nombrePasteleria = nombreNuevo
                for (pastel in leerPasteles()) {
                    if (pastel.nombrePasteleria == nombreViejo) {
                        val nuevoPastel = pastel.copy(nombrePasteleria = nombreNuevo)
                        actualizarPastel(pastel.nombre, nuevoPastel)
                    }
                }
            }
        }
    }
    override fun toString(): String {
        return "Nombre: $nombre, Nombre de la Pastelería: $nombrePasteleria, " +
                "Fecha de Fabricación: ${dateFormat.format(fechaFabricacion)}, " +
                "Número de Pasteles: $numPasteles, Apto para Diabéticos: $aptoDiabeticos, " +
                "Precio: $precio"
    }

}