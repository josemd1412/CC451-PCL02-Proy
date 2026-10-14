package com.example.cc451_pcl02_proy.domain

/** Nivel de severidad. Cada nivel se distingue por forma, palabra y patrón háptico (RNF-04). */
enum class Severity(
    val word: String,
    val hint: String,
    /** Patrón de vibración: pausa, pulso, pausa, pulso... en ms. */
    val vibration: LongArray,
    val repeats: Boolean,
) {
    OK("OK", "Reporte enviado", longArrayOf(0, 400), false),
    CAUTION("PRECAUCIÓN", "Aléjate y señaliza", longArrayOf(0, 350, 250, 350), false),
    DANGER("PELIGRO", "Brigada avisada", longArrayOf(0, 900, 100), true),
}
