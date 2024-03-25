package com.endcodev.beautifullogin.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.endcodev.beautifullogin.presentation.ui.components.MovingBackground
import com.endcodev.beautifullogin.presentation.ui.components.initPlayer


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun RootNavGraph() {
    val navController = rememberNavController()

    var a by remember { mutableStateOf(true) }

    val context = LocalContext.current
    val player = initPlayer(context)
    if (a) {
        MovingBackground(player)
        player.prepare()
        player.play()
    } else {
        player.stop()
        player.clearMediaItems()
    }


    NavHost(
        navController = navController,
        route = RootGraph.ROOT,
        startDestination = RootGraph.AUTH
    ) {
        authNavGraph(navController = navController)
        composable(route = RootGraph.HOME) {//todo
        }
    }
}

object RootGraph {
    const val ROOT = "ROOT"
    const val AUTH = "AUTH"
    const val HOME = "HOME"
}
