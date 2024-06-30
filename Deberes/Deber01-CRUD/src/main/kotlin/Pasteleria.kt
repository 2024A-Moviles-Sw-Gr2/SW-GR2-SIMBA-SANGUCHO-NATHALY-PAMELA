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

        fun crearPasteleria(pasteleria: Pasteleria) {
            val pastelerias = leerPasteleria().toMutableList()
            pastelerias.add(pasteleria)
            guardarPastelerias(pastelerias)
            println("Pastelería creada exitosamente")
        }

        fun leerPasteleria(): List<Pasteleria> {
            val pastelerias = mutableListOf<Pasteleria>()
            pasteleriaArchivo.readLines().forEach { line ->
                if (line.isNotBlank()) {
                    try {
                        val info = line.split(",")
                        if (info.size >= 5) {
                            val nombrePasteleria = info[0]
                            val fechaApertura = dateFormat.parse(info[1])
                            val entregaADomicilio = info[2].toBoolean()
                            val numEmpleados = info[3].toInt()
                            val ingresos = info[4].toDouble()

                            val nuevaPasteleria = Pasteleria(
                                nombrePasteleria,
                                fechaApertura,
                                entregaADomicilio,
                                numEmpleados,
                                ingresos
                            )

                            val pasteles = Pastel.leerPasteles().filter { it.nombrePasteleria == nombrePasteleria }
                            nuevaPasteleria.pasteles.addAll(pasteles)

                            pastelerias.add(nuevaPasteleria)
                        } else {
                            println("Línea con formato incorrecto")
                        }
                    } catch (e: Exception) {
                        println("Error al procesar la línea")
                    }
                }
            }
            return pastelerias
        }

        fun guardarPastelerias(pastelerias: List<Pasteleria>) {
            pasteleriaArchivo.writeText("")
            pastelerias.forEach { pasteleria ->
                pasteleriaArchivo.appendText(
                    "${pasteleria.nombrePasteleria},${dateFormat.format(pasteleria.fechaApertura)}," +
                            "${pasteleria.entregaADomicilio},${pasteleria.numEmpleados},${pasteleria.ingresos} \n"
                )
            }
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

                val pasteles = Pastel.leerPasteles().toMutableList()

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
            val pastelerias = leerPasteleria()
            val pasteleriaExiste = pastelerias.any { it.nombrePasteleria == nombre }

            if (pasteleriaExiste) {
                val pasteleriasActualizadas = pastelerias.filterNot { it.nombrePasteleria == nombre }
                val pastelesEliminados = Pastel.leerPasteles().filterNot { it.nombrePasteleria == nombre }

                pasteleriaArchivo.writeText(pasteleriasActualizadas.joinToString("\n") {
                    "${it.nombrePasteleria},${dateFormat.format(it.fechaApertura)}," +
                            "${it.entregaADomicilio},${it.numEmpleados},${it.ingresos}"
                })

                pastelesArchivo.writeText(pastelesEliminados.joinToString("\n") {
                    "${it.nombre},${it.nombrePasteleria},${it.fechaFabricacion.time}," +
                            "${it.numPasteles},${it.aptoDiabeticos},${it.precio}"
                })

                println("¡Pastelería '$nombre' eliminada exitosamente junto con sus pasteles!")
            } else {
                println("La pastelería '$nombre' no existe.")
            }
        }
    }

    override fun toString(): String {
        return "Pasteleria: $nombrePasteleria, Fecha de Apertura: ${dateFormat.format(fechaApertura)}, " +
                "Entrega a Domicilio: $entregaADomicilio, Num. de Empleados: $numEmpleados, Ingresos: $ingresos"
    }
}