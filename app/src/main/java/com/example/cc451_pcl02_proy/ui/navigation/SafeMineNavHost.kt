package com.example.cc451_pcl02_proy.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cc451_pcl02_proy.data.MockData
import com.example.cc451_pcl02_proy.ui.operario.ConfirmScreen
import com.example.cc451_pcl02_proy.ui.operario.ListeningScreen

@Composable
fun SafeMineNavHost() {
    val nav = rememberNavController()
    // La demo cicla por los tres reportes simulados: precaución, OK y peligro.
    var nextDemo by remember { mutableIntStateOf(0) }

    NavHost(nav, startDestination = Routes.LISTENING) {
        composable(Routes.LISTENING) {
            ListeningScreen(onSimulateVoice = {
                nav.navigate(Routes.confirm(nextDemo))
                nextDemo = (nextDemo + 1) % MockData.voiceDemos.size
            })
        }
        composable(Routes.CONFIRM, arguments = listOf(navArgument("demo") { type = NavType.IntType })) { entry ->
            val report = MockData.voiceDemos[entry.arguments?.getInt("demo") ?: 0]
            ConfirmScreen(
                report = report,
                onConfirm = {},
                onCorrect = { nav.popBackStack() },
            )
        }
    }
}
