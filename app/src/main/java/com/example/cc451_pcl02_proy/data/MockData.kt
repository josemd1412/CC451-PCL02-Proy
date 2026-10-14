package com.example.cc451_pcl02_proy.data

import com.example.cc451_pcl02_proy.domain.Report
import com.example.cc451_pcl02_proy.domain.ReportStatus
import com.example.cc451_pcl02_proy.domain.Severity

data class QuickPhrase(val label: String, val report: Report)

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

    /** Frases rápidas por área (RF-21): botón grande o decir su número. */
    val quickPhrases = listOf(
        QuickPhrase("Derrame", Report(201, "Derrame de aceite", 3, Severity.CAUTION, "Frase rápida: derrame", "22:40",
            protocol = listOf("Aléjate.", "Señaliza el área.", "Espera al supervisor."))),
        QuickPhrase("Cable pelado", Report(202, "Cable pelado", 3, Severity.CAUTION, "Frase rápida: cable pelado", "22:41",
            protocol = listOf("No lo toques.", "Señaliza el área.", "Avisa al supervisor."))),
        QuickPhrase("Falta desatado", Report(203, "Falta desatado", 3, Severity.CAUTION, "Frase rápida: falta desatado", "22:42",
            protocol = listOf("Aléjate del techo.", "Señaliza la labor."))),
        QuickPhrase("Piso resbaloso", Report(204, "Piso resbaloso", 3, Severity.OK, "Frase rápida: piso resbaloso", "22:43",
            protocol = listOf("Camina con cuidado.", "Señaliza si puedes."))),
        QuickPhrase("Caída de rocas", Report(205, "Caída de rocas", 3, Severity.DANGER, "Frase rápida: caída de rocas", "22:44",
            protocol = listOf("Evacúa la labor.", "Mantén la calma.", "La brigada va en camino."))),
        QuickPhrase("Gas", Report(206, "Posible fuga de gas", 3, Severity.DANGER, "Frase rápida: gas", "22:45",
            protocol = listOf("Sal de la zona.", "No uses fuego.", "La brigada va en camino."))),
        QuickPhrase("Incendio", Report(207, "Incendio", 3, Severity.DANGER, "Frase rápida: incendio", "22:46",
            protocol = listOf("Evacúa por la ruta verde.", "Activa la alarma.", "La brigada va en camino."))),
        QuickPhrase("Equipo averiado", Report(208, "Equipo averiado", 3, Severity.OK, "Frase rápida: equipo averiado", "22:47",
            protocol = listOf("Apaga el equipo.", "Etiquétalo.", "Avisa al supervisor."))),
    )

    /** Todos los reportes que la navegación puede abrir por índice: demos de voz y frases rápidas. */
    val allDemos: List<Report> = voiceDemos + quickPhrases.map { it.report }

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
