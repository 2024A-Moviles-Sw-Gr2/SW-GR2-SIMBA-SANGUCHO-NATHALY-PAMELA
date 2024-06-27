fun main() {
    var opcion = 0

    while (opcion < 9) {
        opcion = menu()

        when (opcion) {
            1 -> {
                val nuevaPasteleria = Pasteleria.ingresarDatosPasteleria()
                Pasteleria.crearPasteleria(nuevaPasteleria)
            }
            2 -> {
                val nuevoPastel = Pastel.ingresarDatosPastel()
                Pastel.crearPastel(nuevoPastel, nuevoPastel.nombrePasteleria)
            }
            3 -> {
                Pasteleria.leerPasteleria().forEach { pasteleria ->
                    println(pasteleria)
                    pasteleria.pasteles.forEach { pasteles ->
                        println("  - $pasteles")
                    }
                }
            }
            4 -> {
                Pastel.leerPasteles().forEach {
                    println(it)
                }
            }
            5 -> {
                val nombre = Pasteleria.modificarPorNombre()
                val nuevaPasteleria = Pasteleria.ingresarDatosPasteleria()
                Pasteleria.actualizarPasteleria(nombre, nuevaPasteleria)
            }
            6 -> {
                val nombrePastel = Pastel.modificarPorNombre()
                val nuevoPastel = Pastel.ingresarDatosPastel()
                Pastel.actualizarPastel(nombrePastel, nuevoPastel)
            }
            7 -> {
                val eliminar = Pasteleria.eliminarPorNombre()
                Pasteleria.eliminarPasteleria(eliminar)
            }
            8 -> {
                val eliminar =Pastel.eliminarPorNombre()
                Pastel.eliminarPastel(eliminar)
            }
            9 -> {
                println("Saliendo del programa...")
                break
            }
            else -> println("Opción no válida. Inténtalo de nuevo.")
        }
    }
}

fun menu(): Int {
    var opc = 0
    var num = " "

    num += "PASTELERIA NATH\n"
    num += "1. AGREGAR PASTELERIA\n"
    num += "2. AGREGAR PASTEL\n"
    num += "3. VER LISTADO DE PASTELERIA\n"
    num += "4. VER LISTADO DE PASTELES\n"
    num += "5. ACTUALIZAR PASTELERIA\n"
    num += "6. ACTUALIZAR PASTEL\n"
    num += "7. ELIMINAR  PASTELERIA\n"
    num += "8. ELIMINAR PASTEL\n"
    num += "9. CERRAR\n"
    num += "\n"
    num += "Escoje el numero de opcion que deseas: "

    println(num)
    opc = readLine()?.toIntOrNull() ?: 0
    return opc
}
