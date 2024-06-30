import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun main() {
    var opcion = 0

    while (opcion < 4) {
        opcion = opciones()

        when (opcion) {
            1 -> {
                var opcionPasteleria = 0

                while (opcionPasteleria < 5) {
                    opcionPasteleria = menuPasteleria()

                    when (opcionPasteleria) {
                        1 -> {
                            val nuevaPasteleria = ingresarDatosPasteleria()
                            Pasteleria.crearPasteleria(nuevaPasteleria)
                        }
                        2 -> {
                            println("PASTELERIAS NATH")
                            Pasteleria.leerPasteleria().forEach {
                                println(it)
                            }
                        }
                        3 -> {
                            val nombre = modificarPorNombre()
                            val nuevaPasteleria = ingresarDatosPasteleria()
                            Pasteleria.actualizarPasteleria(nombre, nuevaPasteleria)
                        }
                        4 -> {
                            val eliminar = eliminarPorNombre()
                            Pasteleria.eliminarPasteleria(eliminar)
                        }
                        5 -> {
                        }
                        else -> println("Opción no válida. Inténtalo de nuevo.")
                    }
                }
            }
            2 -> {
                var opcionPastel = 0

                while (opcionPastel < 5) {
                    opcionPastel = menuPastel()

                    when (opcionPastel) {
                        1 -> {
                            val nuevoPastel = ingresarDatosPastel()
                            Pastel.crearPastel(nuevoPastel, nuevoPastel.nombrePasteleria)
                        }
                        2 -> {
                            println("PASTELERIAS NATH")
                            Pastel.leerPasteles().forEach {
                                println(it)
                            }
                        }
                        3 -> {
                            val nombrePastel = modificarPorNombre()
                            val nuevoPastel = ingresarDatosPastel()
                            Pastel.actualizarPastel(nombrePastel, nuevoPastel)
                        }
                        4 -> {
                            val eliminar = eliminarPorNombre()
                            Pastel.eliminarPastel(eliminar)
                        }
                        5 -> {
                        }
                        else -> println("Opción no válida. Inténtalo de nuevo.")
                    }
                }
            }
            3 -> {
                println("PASTELERIAS NATH")
                Pasteleria.leerPasteleria().forEach { pasteleria ->
                    println(pasteleria)
                    if (pasteleria.pasteles.isEmpty()) {
                    } else {
                        var count = 1
                        pasteleria.pasteles.forEach { pastel ->
                            println("  $count. $pastel")
                            count++
                        }
                    }
                }
            }
            4 -> {
                println("Saliendo del programa...")
                break
            }
            else -> println("Opción no válida. Inténtalo de nuevo.")
        }
    }
}

fun menuPasteleria(): Int {
    var opc = 0
    var num = ""

    num += "PASTELERIAS NATH\n"
    num += "1. AGREGAR PASTELERIA\n"
    num += "2. VER LISTADO DE PASTELERIA\n"
    num += "3. ACTUALIZAR PASTELERIA\n"
    num += "4. ELIMINAR PASTELERIA\n"
    num += "5. REGRESAR\n"
    num += "\n"
    num += "Escoje el numero de opcion que deseas: "

    println(num)
    opc = readLine()?.toIntOrNull() ?: 0
    return opc
}

fun menuPastel(): Int {
    var opc = 0
    var num = ""

    num += "PASTELERIAS NATH\n"
    num += "1. AGREGAR PASTEL\n"
    num += "2. VER LISTADO DE PASTELES\n"
    num += "3. ACTUALIZAR PASTEL\n"
    num += "4. ELIMINAR PASTEL\n"
    num += "5. REGRESAR\n"
    num += "\n"
    num += "Escoje el numero de opcion que deseas: "

    println(num)
    opc = readLine()?.toIntOrNull() ?: 0
    return opc
}

fun opciones(): Int {
    var opc = 0
    var num = ""

    num += "PASTELERIAS NATH\n"
    num += "1. PASTELERIA\n"
    num += "2. PASTEL\n"
    num += "3. VER LISTA DE PASTELES Y PASTELERIAS\n"
    num += "4. CERRAR\n"
    num += "\n"
    num += "Escoje el numero de opcion que deseas: "

    println(num)
    opc = readLine()?.toIntOrNull() ?: 0
    return opc
}

fun ingresarDatosPasteleria(): Pasteleria {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    println("Ingrese nombre de la pasteleria:")
    val nombre = readLine().orEmpty()

    var fechaApertura: Date
    while (true) {
        println("Ingrese la fecha de apertura (yyyy-MM-dd):")
        val fechaInput = readLine().orEmpty()
        try {
            dateFormat.isLenient = false
            fechaApertura = dateFormat.parse(fechaInput)
            break
        } catch (e: ParseException) {
            println("El formato de fecha está incorrecto. Por favor, ingrese la fecha en formato yyyy-MM-dd.")
        }
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
        println("Ingrese número de empleados:")
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

fun ingresarDatosPastel(): Pastel {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd")

    println("Ingrese el nombre del pastel:")
    val nombre = readLine().orEmpty()

    println("Ingrese nombre de la pasteleria:")
    val nombrePasteleria = readLine().orEmpty()

    var fechaFabricacion: Date
    while (true) {
        println("Ingrese la fecha de fabricacion (yyyy-MM-dd):")
        val fechaInput = readLine().orEmpty()
        try {
            dateFormat.isLenient = false
            fechaFabricacion = dateFormat.parse(fechaInput)
            break
        } catch (e: ParseException) {
            println("Formato de fecha incorrecto.")
        }
    }

    println("Ingrese el número de pasteles:")
    val numPasteles = readLine()?.toIntOrNull() ?: 0

    val aptoDiabeticos: Boolean
    while (true) {
        println("¿Es Apto para Diabeticos? (si/no):")
        val entregaADomicilioInput = readLine().orEmpty().toLowerCase()
        aptoDiabeticos = when (entregaADomicilioInput) {
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
        println("Ingrese el precio del pastel:")
        val ingresosInput = readLine().orEmpty().replace(',', '.')
        precio = try {
            ingresosInput.toDouble()
        } catch (e: Exception) {
            println("Por favor, ingrese un número decimal")
            continue
        }
        break
    }

    return Pastel(nombre, nombrePasteleria, fechaFabricacion, numPasteles, aptoDiabeticos, precio)
}

fun modificarPorNombre(): String {
    println("Ingrese nombre a actualizar:")
    return readLine().orEmpty()
}

fun eliminarPorNombre(): String {
    println("Ingrese nombre a eliminar:")
    return readLine().orEmpty()
}
