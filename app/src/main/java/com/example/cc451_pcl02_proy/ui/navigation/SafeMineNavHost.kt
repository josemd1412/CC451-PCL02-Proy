package com.example.cc451_pcl02_proy.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cc451_pcl02_proy.data.MockData
import com.example.cc451_pcl02_proy.ui.operario.ConfirmScreen
import com.example.cc451_pcl02_proy.ui.operario.CrewScreen
import com.example.cc451_pcl02_proy.ui.operario.HelpScreen
import com.example.cc451_pcl02_proy.ui.operario.ListeningScreen
import com.example.cc451_pcl02_proy.ui.operario.ProfileScreen
import com.example.cc451_pcl02_proy.ui.operario.QuickPhrasesScreen
import com.example.cc451_pcl02_proy.ui.operario.SosScreen
import com.example.cc451_pcl02_proy.ui.operario.StartShiftScreen
import com.example.cc451_pcl02_proy.ui.supervisor.AlertDetailScreen
import com.example.cc451_pcl02_proy.ui.supervisor.DashboardScreen
import com.example.cc451_pcl02_proy.ui.operario.TrafficLightScreen

@Composable
fun SafeMineNavHost() {
    val nav = rememberNavController()
    // La demo cicla por los tres reportes simulados: precaución, OK y peligro.
    // Estado compartido del supervisor: confirmar o cerrar actualiza dashboard y detalle.
    val reports = remember { mutableStateListOf(*MockData.supervisorReports.toTypedArray()) }
    var nextDemo by remember { mutableIntStateOf(0) }

    NavHost(nav, startDestination = Routes.START) {
        composable(Routes.START) {
            StartShiftScreen(
                onScanOk = {
                    nav.navigate(Routes.LISTENING) { popUpTo(Routes.START) { inclusive = true } }
                },
                onSupervisor = { nav.navigate(Routes.DASHBOARD) },
            )
        }
        composable(Routes.LISTENING) {
            ListeningScreen(
                onSimulateVoice = {
                    nav.navigate(Routes.confirm(nextDemo))
                    nextDemo = (nextDemo + 1) % MockData.voiceDemos.size
                },
                onSos = { nav.navigate(Routes.SOS) },
                onPhrases = { nav.navigate(Routes.PHRASES) },
                onHelp = { nav.navigate(Routes.HELP) },
                onProfile = { nav.navigate(Routes.PROFILE) },
            )
        }
        composable(Routes.CONFIRM, arguments = listOf(navArgument("demo") { type = NavType.IntType })) { entry ->
            val report = MockData.allDemos[entry.arguments?.getInt("demo") ?: 0]
            ConfirmScreen(
                report = report,
                onConfirm = { nav.navigate(Routes.traffic(entry.arguments?.getInt("demo") ?: 0)) },
                onCorrect = { nav.popBackStack() },
            )
        }
        composable(Routes.DASHBOARD) {
            DashboardScreen(
                reports = reports,
                onOpen = { nav.navigate(Routes.alert(it)) },
                onExit = { nav.popBackStack() },
            )
        }
        composable(Routes.ALERT, arguments = listOf(navArgument("id") { type = NavType.IntType })) { entry ->
            val id = entry.arguments?.getInt("id")
            val index = reports.indexOfFirst { it.id == id }
            if (index >= 0) {
                AlertDetailScreen(
                    report = reports[index],
                    onChange = { reports[index] = it },
                    onBack = { nav.popBackStack() },
                )
            }
        }
        composable(Routes.PROFILE) {
            ProfileScreen(onCrew = { nav.navigate(Routes.CREW) }, onBack = { nav.popBackStack() })
        }
        composable(Routes.CREW) {
            CrewScreen(onBack = { nav.popBackStack() })
        }
        composable(Routes.HELP) {
            HelpScreen(onBack = { nav.popBackStack() })
        }
        composable(Routes.PHRASES) {
            QuickPhrasesScreen(
                // Las frases rápidas van después de las 3 demos de voz en MockData.allDemos.
                onPick = { nav.navigate(Routes.confirm(MockData.voiceDemos.size + it)) },
                onBack = { nav.popBackStack() },
            )
        }
        composable(Routes.SOS) {
            SosScreen(onBack = { nav.popBackStack() })
        }
        composable(Routes.TRAFFIC, arguments = listOf(navArgument("demo") { type = NavType.IntType })) { entry ->
            val report = MockData.allDemos[entry.arguments?.getInt("demo") ?: 0]
            TrafficLightScreen(
                report = report,
                onDismiss = { nav.popBackStack(Routes.LISTENING, inclusive = false) },
            )
        }
    }
}
