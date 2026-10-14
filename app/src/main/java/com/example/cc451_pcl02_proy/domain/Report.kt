package com.example.cc451_pcl02_proy.domain

enum class ReportStatus(val label: String) { NEW("Nuevo"), IN_PROGRESS("En atención"), CLOSED("Cerrado") }

data class Report(
    val id: Int,
    val type: String,
    val zone: Int,
    val severity: Severity,
    val transcript: String,
    val time: String,
    val status: ReportStatus = ReportStatus.NEW,
    val protocol: List<String> = emptyList(),
    /** Acción correctiva asignada por el supervisor (RF-09). */
    val action: String? = null,
) {
    /** Resumen que la app lee y muestra antes de enviar (RF-06). */
    val summary: String get() = "$type, zona $zone, ${severity.word.lowercase()}"
}
