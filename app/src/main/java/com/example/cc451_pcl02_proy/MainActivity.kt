package com.example.cc451_pcl02_proy

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.cc451_pcl02_proy.ui.navigation.SafeMineNavHost
import com.example.cc451_pcl02_proy.ui.theme.CC451PCL02ProyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
        )
        setContent {
            CC451PCL02ProyTheme {
                SafeMineNavHost()
            }
        }
    }
}
