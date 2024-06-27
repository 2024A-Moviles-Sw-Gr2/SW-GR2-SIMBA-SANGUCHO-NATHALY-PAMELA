package com.example.a2024swgr2ssnp

class BBaseDatosMemoria {

    companion object{
        val arregloBEntrenador = arrayListOf<BEntrenador>()

        init {
            arregloBEntrenador
                .add(
                    BEntrenador(1,"Nathaly", "a@a.com")
                )
            arregloBEntrenador
                .add(
                    BEntrenador(2,"Pamela", "b@b.com")
                )
            arregloBEntrenador
                .add(
                    BEntrenador(3,"Inez", "c@c.com")
                )
        }
    }
}