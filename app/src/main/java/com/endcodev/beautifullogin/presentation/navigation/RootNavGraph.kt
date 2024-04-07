package com.endcodev.beautifullogin.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.endcodev.beautifullogin.presentation.ui.components.MovingBackground
import com.endcodev.beautifullogin.presentation.ui.components.initPlayer

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun RootNavGraph() {
    val navController: NavHostController = rememberNavController()

    val player = initPlayer(context = LocalContext.current)
    MovingBackground(player)
    player.prepare()
    player.play()

    NavHost(
        navController = navController,
        route = RootGraph.ROOT,
        startDestination = RootGraph.AUTH
    ) {
        authNavGraph(navController = navController)
        homeNavGraph(navController = navController)
    }
}

sealed class HomeGraph {
    data object START : AuthGraph(route = "START")
    data object PROFILE : AuthGraph(route = "PROFILE")
}

object RootGraph {
    const val ROOT = "ROOT"
    const val AUTH = "AUTH"
    const val HOME = "HOME"
}

