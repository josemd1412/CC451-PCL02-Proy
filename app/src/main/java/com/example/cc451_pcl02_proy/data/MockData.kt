package com.example.cc451_pcl02_proy.data

import com.example.cc451_pcl02_proy.domain.Report
import com.example.cc451_pcl02_proy.domain.ReportStatus
import com.example.cc451_pcl02_proy.domain.Severity

/** Datos simulados para el MVP de diseño (RF-19). No hay backend en esta entrega. */
object MockData {
    /** Reportes que el operario "dice" en la demo; uno por cada nivel de severidad. */
    val voiceDemos = listOf(
        Report(
            id = 101, type = "Derrame de aceite", zone = 3, severity = Severity.CAUTION,
            transcript = "SafeMine, reporto derrame de aceite en zona 3", time = "21:42",
            protocol = listOf("Aléjate.", "Señaliza el área.", "Espera al supervisor."),
        ),
        Report(
            id = 102, type = "Orden y limpieza", zone = 3, severity = Severity.OK,
            transcript = "SafeMine, reporto material ordenado en zona 3", time = "22:05",
            protocol = listOf("Sigue con tu tarea.", "Gracias por reportar."),
        ),
        Report(
            id = 103, type = "Trabajador atrapado", zone = 3, severity = Severity.DANGER,
            transcript = "SafeMine, reporto compañero atrapado en zona 3", time = "22:31",
            protocol = listOf("No lo muevas.", "Mantén la calma.", "La brigada va en camino."),
        ),
    )

    val supervisorReports = listOf(
        voiceDemos[2].copy(id = 1, time = "22:31"),
        Report(2, "Caída de rocas", 2, Severity.DANGER, "Se desprendió roca en la labor dos", "22:10", ReportStatus.IN_PROGRESS,
            listOf("Evacúa la labor.", "Señaliza el acceso.")),
        voiceDemos[0].copy(id = 3, time = "21:42"),
        Report(4, "Cable pelado", 1, Severity.CAUTION, "Reporto cable pelado en el tablero", "21:15", ReportStatus.IN_PROGRESS),
        Report(5, "Falta desatado", 3, Severity.CAUTION, "Falta desatado en la labor tres", "20:50", ReportStatus.CLOSED),
        Report(6, "Zona despejada", 4, Severity.OK, "Zona cuatro sin novedades", "20:20", ReportStatus.CLOSED),
    )
}
