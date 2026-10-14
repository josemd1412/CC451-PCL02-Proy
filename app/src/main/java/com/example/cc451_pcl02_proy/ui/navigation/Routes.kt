package com.example.cc451_pcl02_proy.ui.navigation

/** Rutas de la app. Los argumentos se pasan en la ruta, ej. `confirm/0`. */
object Routes {
    const val LISTENING = "listening"
    const val CONFIRM = "confirm/{demo}"
    fun confirm(demo: Int) = "confirm/$demo"
}
