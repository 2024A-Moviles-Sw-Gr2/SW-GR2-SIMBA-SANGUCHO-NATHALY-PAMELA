import Pastel.Companion.leerPasteles
import Pastel.Companion.pastelesArchivo
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

data class Pasteleria(
    var nombrePasteleria: String,
    var fechaApertura: Date,
    var entregaADomicilio: Boolean,
    var numEmpleados: Int,
    var ingresos: Double,
    val pasteles: ArrayList<Pastel> = ArrayList()
) {
    companion object {
        private val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        private val pasteleriaArchivo = File("src/main/resources/Pasteleria.txt")

        init {
            if (!pasteleriaArchivo.exists()) pasteleriaArchivo.createNewFile()
        }

        fun ingresarDatosPasteleria(): Pasteleria {
            println("Ingrese nombre de la pasteleria:")
            val nombre = readLine().orEmpty()

            val fechaApertura: Date
            while (true) {
                println("Ingrese la fecha de apertura (yyyy-MM-dd):")
                val fechaInput = readLine().orEmpty()
                fechaApertura = try {
                    dateFormat.parse(fechaInput)
                } catch (e: Exception) {
                    println("El formato de fecha esta incorrecto")
                    continue
                }
                break
            }

            val entregaADomicilio: Boolean
            while (true) {
                println("¿Hay entregas a domicilio? (si/no):")
                val entregaADomicilioInput = readLine().orEmpty().toLowerCase()
                entregaADomicilio = when (entregaADomicilioInput) {
                    "si" -> true
                    "no" -> false
                    else -> {
                        println("Por favor, ingrese 'si' o 'no'.")
                        continue
                    }
                }
                break
            }

            val numEmpleados: Int
            while (true) {
                println("Ingrese numero de empleados:")
                val numEmpleadosInput = readLine().orEmpty()
                numEmpleados = try {
                    numEmpleadosInput.toInt()
                } catch (e: Exception) {
                    println("Por favor, ingrese un número entero")
                    continue
                }
                break
            }

            val ingresos: Double
            while (true) {
                println("Ingrese los ingresos:")
                val ingresosInput = readLine().orEmpty().replace(',', '.')
                ingresos = try {
                    ingresosInput.toDouble()
                } catch (e: Exception) {
                    println("Por favor, ingrese un número decimal")
                    continue
                }
                break
            }

            return Pasteleria(nombre, fechaApertura, entregaADomicilio, numEmpleados, ingresos)
        }

        fun crearPasteleria(pasteleria: Pasteleria) {
            pasteleriaArchivo.appendText("\n${pasteleria.nombrePasteleria},${dateFormat.format(pasteleria.fechaApertura)}," +
                    "${pasteleria.entregaADomicilio},${pasteleria.numEmpleados},${pasteleria.ingresos}\n")

        }

        fun leerPasteleria(): List<Pasteleria> {
            val pastelerias = pasteleriaArchivo.readLines().mapNotNull { line ->
                val info = line.split(",")
                if (info.size >= 5) {
                    try {
                        Pasteleria(
                            info[0],
                            dateFormat.parse(info[1]),
                            info[2].toBoolean(),
                            info[3].toInt(),
                            info[4].toDouble()
                        )
                    } catch (e: Exception) {
                        println("Error al procesar la línea: $line, error: ${e.message}")
                        null
                    }
                } else {
                    println("Linea en blanco: $line")
                    null
                }
            }

            val pasteles = Pastel.leerPasteles()

            pastelerias.forEach { pasteleria ->
                pasteleria.pasteles.addAll(pasteles.filter { it.nombrePasteleria == pasteleria.nombrePasteleria })
            }

            return pastelerias
        }

        fun actualizarPasteleria(nombrePasteleria: String, nuevoNombrePasteleria: Pasteleria) {
            val pastelerias = leerPasteleria().toMutableList()
            val index = pastelerias.indexOfFirst { it.nombrePasteleria == nombrePasteleria }
            if (index != -1) {
                pastelerias[index] = nuevoNombrePasteleria
                pasteleriaArchivo.writeText(pastelerias.joinToString("\n") {
                    "${it.nombrePasteleria},${dateFormat.format(it.fechaApertura)}," +
                            "${it.entregaADomicilio},${it.numEmpleados},${it.ingresos}"
                })

                // Obtener los pasteles asociados a la pastelería actualizada
                val pasteles = Pastel.leerPasteles().toMutableList()

                // Actualizar nombre en los pasteles asociados
                Pastel.actualizarNombrePasteleria(pasteles, nombrePasteleria, nuevoNombrePasteleria.nombrePasteleria)
            }
            println("¡Pastelería '$nombrePasteleria' actualizada exitosamente a '${nuevoNombrePasteleria.nombrePasteleria}'!")
        }


        fun actualizarEnPasteles(nombrePasteleria: String, nuevaPasteleria: ArrayList<Pastel>) {
            val pastelerias = leerPasteleria().toMutableList()
            val index = pastelerias.indexOfFirst { it.nombrePasteleria == nombrePasteleria }
            if (index != -1) {
                pastelerias[index].pasteles.clear()
                pastelerias[index].pasteles.addAll(nuevaPasteleria)
                pasteleriaArchivo.writeText(pastelerias.joinToString("\n") {
                    "${it.nombrePasteleria},${dateFormat.format(it.fechaApertura)}," +
                            "${it.entregaADomicilio},${it.numEmpleados},${it.ingresos}"
                })
            }
        }

        fun eliminarPasteleria(nombre: String) {
            val pastelerias = leerPasteleria().filterNot { it.nombrePasteleria == nombre }
            val pastelesEliminados = leerPasteles().filterNot { it.nombrePasteleria == nombre }

            pasteleriaArchivo.writeText(pastelerias.joinToString("\n") {
                "${it.nombrePasteleria},${dateFormat.format(it.fechaApertura)}," +
                        "${it.entregaADomicilio},${it.numEmpleados},${it.ingresos}"
            })

            pastelesArchivo.writeText(pastelesEliminados.joinToString("\n") {
                "${it.nombre},${it.nombrePasteleria},${it.fechaFabricacion.time}," +
                        "${it.numPasteles},${it.aptoDiabeticos},${it.precio}"
            })

            println("¡Pastelería '$nombre' eliminada exitosamente junto con sus pasteles!")
        }
        fun modificarPorNombre(): String {
            println("Ingrese nombre de la pasteleria a actualizar:")
            return readLine().orEmpty()
        }
        fun eliminarPorNombre(): String {
            println("Ingrese nombre de la pasteleria a eliminar:")
            return readLine().orEmpty()
        }
        fun guardarPasteleria(pastelerias: List<Pasteleria>) {
            pasteleriaArchivo.writeText(pastelerias.joinToString("\n") {
                "${it.nombrePasteleria},${dateFormat.format(it.fechaApertura)}," +
                        "${it.entregaADomicilio},${it.numEmpleados},${it.ingresos}"
            })
        }
    }

    override fun toString(): String {
        return "Nombre: $nombrePasteleria, Fecha de Apertura: ${dateFormat.format(fechaApertura)}, " +
                "Entrega a Domicilio: $entregaADomicilio, Num. de Empleados: $numEmpleados, Ingresos: $ingresos"
    }
}
