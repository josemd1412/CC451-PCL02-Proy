package com.example.cc451_pcl02_proy.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cc451_pcl02_proy.ui.operario.ListeningScreen

@Composable
fun SafeMineNavHost() {
    val nav = rememberNavController()
    NavHost(nav, startDestination = Routes.LISTENING) {
        composable(Routes.LISTENING) {
            ListeningScreen(onSimulateVoice = {})
        }
    }
}
