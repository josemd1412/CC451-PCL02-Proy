package com.example.cc451_pcl02_proy.ui.navigation

/** Rutas de la app. Los argumentos se pasan en la ruta, ej. `confirm/0`. */
object Routes {
    const val DASHBOARD = "dashboard"
    const val ALERT = "alert/{id}"
    fun alert(id: Int) = "alert/$id"
    const val START = "start"
    const val LISTENING = "listening"
    const val CONFIRM = "confirm/{demo}"
    const val PROFILE = "profile"
    const val CREW = "crew"
    const val HELP = "help"
    const val MANUAL = "manual"
    const val PHRASES = "phrases"
    const val SOS = "sos"
    const val TRAFFIC = "traffic/{demo}"
    fun traffic(demo: Int) = "traffic/$demo"
    fun confirm(demo: Int) = "confirm/$demo"
}
