import java.util.*
import kotlin.collections.ArrayList

fun main() {
    println("Hola mundo :)");

    //Variable inmutable

    val inmutable: String = "Adrian";

    //Si se intenta poner =, se va a tener un error, no va a dejar, es una variable de "solo lectura"

    //Variable mutable

    var mutable: String = "Vicente"
    mutable = "Adrian" // OK
    //VAL > VAR

    var ejemploVariable = "Adrian Erguez"
    val edadEjemplo: Int = 12
    //trim(): elimina los espacios en blanco
    ejemploVariable.trim()
    //ejemploVariable = edadEjemplo // edad de tipo incorrecto


    //Variables primitivas

    val nombre: String = "Adrian"
    val sueldo: Double = 1.2
    val estadoCivil: Char = 'C'
    val mayorEdad: Boolean = true

    //Clase Java
    val fechaDeNacimiento: Date = Date()

    // when (Switch)

    val estadoCivilWhen = "C"
    when (estadoCivilWhen) {
        ("C") -> {
            println("Casado")
        }
        "S" -> {
            println("Soltero")
        }
        else -> {
            println("No sabemos")
        }

    }

    val esSoltero = (estadoCivilWhen == "S")
    val coqueteo = if (esSoltero) "Si" else "No" // if else chiquito

    calcularSueldo(10.00)
    calcularSueldo(10.00, 15.00, 20.00)
    //Named parameters

    // calcularSueldo (sueldo, tasa, bonoEspecial)
    calcularSueldo(10.00, bonoEspecial = 20.00)
    calcularSueldo(bonoEspecial = 20.00, sueldo = 10.00, tasa = 14.00)
    //Uso de clases
    val sumaUno = Suma(1,1)// new Suma(1,1) en Kotlin no hay new
    val sumaDos = Suma(null, 1)
    val sumaTres= Suma(1, null)
    val sumaCuatro = Suma(null,null)
    sumaUno.sumar()
    sumaDos.sumar()
    sumaTres.sumar()
    sumaCuatro.sumar()
    println(Suma.pi)
    println(Suma.elevarAlCuadrado(2))
    println(Suma.historialSumas)


    //Arreglos Estaticos
    val arregloEstatico: Array<Int> =  arrayOf(1,2,3)
    println(arregloEstatico);
    //Arreglos Dinamicos
    val ArregloDinamico: ArrayList<Int> = arrayListOf(
        1,2,3,4,5,6,7,8,9,10
    )
    println(ArregloDinamico)


    //ForEach => Unit
    //Iterar un arreglo

    val respuestaForEach: Unit = ArregloDinamico
        .forEach{ valorActual: Int -> // ->
            println("Valor Actual (it): ${valorActual}")
        }
    //"it" (en ingles "eso") significa el elemento iterado
    ArregloDinamico.forEach{ println("Valor Actual (it) ${it}") }

    // MAP -> MUTA(Modifica cambia) el arreglo
    // 1) Enviamos el nuevo valor de la iteracion
    // 2) Nos devuelve un NUEVO ARREGLO con valores
    // de las iteraciones
    val respuestaMap: List<Double> = ArregloDinamico
        .map { valorActual: Int ->
            return@map valorActual.toDouble() + 100.00
        }
    println(respuestaMap)
    val respuestaMapDos = ArregloDinamico.map{ it + 15 }
    println(respuestaMapDos)

    // Filter -> Filtrar el ARREGLO
    // 1) Devolver una expresion (TRUE o FALSE)
    // 2) Nuevo arreglo FILTRADO
    val respuestaFilter: List<Int> = ArregloDinamico
        .filter { valorActual:Int ->
            // Expresion o CONDICION
            val mayoresACinco: Boolean = valorActual > 5
            return@filter mayoresACinco

        }

    val respuestaFilterDos = ArregloDinamico.filter { it <= 5 } // < =
    println(respuestaFilter)
    println(respuestaFilterDos)

    // OR AND
    // OR-> ANY (Alguno cumple?)
    // And -> ALL (Todos cumplen?)
    val respuestaAny: Boolean = ArregloDinamico
        .any { valorActual:Int ->
            return@any (valorActual > 5)
        }
    println(respuestaAny) // True
    val respuestaAll: Boolean = ArregloDinamico
        .all { valorActual:Int ->
            return@all (valorActual > 5)
        }
    println(respuestaAll) // false

    // REDUCE > Valor acumulado

    // Valor acumulado = O (Siempre empieza en O en Kotlin)

    // [1,2,3,4,5] —> Acumular "SUMAR" estos valores del arreglo
    // valorIteracion1 = valorEmpieza + 1 = 0 +1 = 1 > Iteracion1

    // valorIteracion2 = valorAcumuladolIteracion1 + 2= 1 +2 = 3 > Iteracion2

    // valorIteracion3 = valorAcumuladolIteracion2 + 3 = 3 +3 = 6 > Iteracion3

    // valorIteracion4 = valorAcumuladoIteracion3 + 4 = 6 + 4>= 10 > Iteracion4

    // valorIteracion5 = valorAcumuladolteracion4 + 5 = 10 + 5 = 15 > Iteracioná4

    val respuestaReduce: Int = ArregloDinamico
        .reduce { acumulado: Int, valorActual: Int ->

            return@reduce (acumulado + valorActual) // -> cambiar o usar la lógica de negocio


        }
    println(respuestaReduce);
    // return@reduce acumulado + (itemCarrito.cantidad * itemCarrito.precio)
    // return@filter mayoresACinco


}

    //void -> Unit
    fun imprimirNombre(nombre:String): Unit{
        println("Nombre: ${nombre}") // Template Strings
    }

    fun calcularSueldo(
        sueldo:Double,  // Requerido
        tasa: Double = 12.00, // Optional (defecto)
        bonoEspecial:Double? = null // Opcional (nullable)
        // Variable? -> "?" Es Nulleable (osea que puede en algun momento ser nulo)
    ):Double {
        //Int -> Int? (nullable)
        // String -> String? (nullable)
        // Date - > Date? (nulleable)
        if(bonoEspecial == null){
            return sueldo * (100/tasa)
        }else{
            return sueldo * (100/tasa) * bonoEspecial
        }

    }

abstract class Numeros( //Contructor primario
    protected val numeroUno:Int, //instancia.Uno
    protected val numeroDos:Int, //instancia.Dos

) {
    init { //bloque constructor primario (opcional)
        this.numeroUno
        this.numeroDos
        println("Inicializando ")
    }
}

class Suma( // Constructor primario
    unoParametro: Int, // Parametro
    dosParametro: Int, // Parametros
): Numeros( // Clase papa, Numeros (extendiendo)
    unoParametro,
    dosParametro
){
    public val soyPublicoExplicito:String = "Explicito" // Publicas
    val soyPublicoImplicito:String = "Implicito" // Publicas (propiedades, metodos)
    init{ // Bloque Codigo Constructor primario
        // this.unoParametro // ERROR no existe
        this.numeroUno
        this.numeroDos
        numeroUno // this. OPCIONAL (propiedades, metodos)
        numeroDos // this. OPCIONAL (propiedades, metodos)
        this.soyPublicoExplicito
        soyPublicoImplicito // this. OPCIONAL (propiedades, metodos)
    }

    constructor( // Constructor secundario
        uno:Int?,
        dos:Int
    ):this(
        if(uno== null) 0 else uno,
        dos
    )
    constructor( // Constructor tercero
        uno:Int,
        dos:Int?
    ):this(
        uno,
        if(dos== null) 0 else dos,
    )

    constructor( // Constructor cuarto
        uno:Int?,
        dos:Int?
    ):this(
        if(uno== null) 0 else uno,
        if(dos== null) 0 else dos,
    )
    // public fun sumar()Int{ (Modificar "public" es OPCIONAL
    fun sumar():Int{
        val total = numeroUno + numeroDos
        // Suma.agregarHistorial(total) ("Suma." o "NombreClase." es OPCIONAL)
        agregarHistorial(total)
        return total
    }
    companion object{ // Comparte entre todas las instancias, similar al Static
        // funciones y variables
        val pi = 3.14
        var texto = "Mi barquito trae..."
        fun elevarAlCuadrado(num:Int):Int{
            return num * num
        }
        val historialSumas = arrayListOf<Int>()
        fun agregarHistorial(valorTotalSuma:Int){
            historialSumas.add(valorTotalSuma)
        }
    }
}

