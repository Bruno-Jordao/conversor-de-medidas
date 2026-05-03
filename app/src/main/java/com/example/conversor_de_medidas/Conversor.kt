package com.example.conversor_de_medidas

object Conversor {

    fun metrosParaCentimetros(valor: Double): Double {
        return valor * 100
    }

    fun centimetrosParaMetros(valor: Double): Double {
        return valor / 100
    }

    fun quilometrosParaMetros(valor: Double): Double {
        return valor * 1000
    }

    fun metrosParaQuilometros(valor: Double): Double {
        return valor / 1000
    }
}