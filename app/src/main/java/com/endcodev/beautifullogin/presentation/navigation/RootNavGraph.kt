package com.endcodev.beautifullogin.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.endcodev.beautifullogin.presentation.HomeViewModel
import com.endcodev.beautifullogin.presentation.ui.components.initPlayer
import com.endcodev.beautifullogin.presentation.ui.screens.HomeScreen
import com.endcodev.beautifullogin.presentation.ui.screens.ProfileScreen

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun RootNavGraph() {
    val navController = rememberNavController()
    val context = LocalContext.current
    NavHost(
        navController = navController,
        route = RootGraph.ROOT,
        startDestination = RootGraph.AUTH
    ) {

        val player = initPlayer(context)
        authNavGraph(navController = navController, player)

        navigation(
            route = RootGraph.HOME,
            startDestination = HomeGraph.START.route
        ) {
            composable(route = HomeGraph.START.route) {
                val viewModel: HomeViewModel = viewModel()
                val uiState by viewModel.state.collectAsState()
                HomeScreen(state = uiState, onProfileClick = {
                    navController.navigate(HomeGraph.PROFILE.route)
                })
            }
            composable(route = HomeGraph.PROFILE.route) {
                val viewModel: HomeViewModel = viewModel()
                val uiState by viewModel.state.collectAsState()
                ProfileScreen(
                    uiState,
                    onLogOutClick = { viewModel.disconnectUser() },
                    onDeleteClick = {})
            }
        }
    }
}

sealed class HomeGraph(val route: String) {
    data object START : AuthGraph(route = "START")
    data object PROFILE : AuthGraph(route = "PROFILE")
}

object RootGraph {
    const val ROOT = "ROOT"
    const val AUTH = "AUTH"
    const val HOME = "HOME"
}

