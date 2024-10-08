package com.endcodev.beautifullogin.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.OptIn
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.media3.common.util.UnstableApi
import com.endcodev.beautifullogin.presentation.navigation.RootNavGraph
import com.endcodev.beautifullogin.presentation.ui.theme.BeautifulLoginTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @OptIn(UnstableApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        setContent {
            BeautifulLoginTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    RootNavGraph()
                }
            }
        }
    }
}
