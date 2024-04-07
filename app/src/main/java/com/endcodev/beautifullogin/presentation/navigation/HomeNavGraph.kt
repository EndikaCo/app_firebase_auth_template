package com.endcodev.beautifullogin.presentation.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.endcodev.beautifullogin.presentation.ui.screens.HomeScreen
import com.endcodev.beautifullogin.presentation.ui.screens.ProfileScreen
import com.endcodev.beautifullogin.presentation.viewmodel.HomeViewModel

fun NavGraphBuilder.homeNavGraph(navController: NavHostController) {
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
                onLogOutClick = {
                    viewModel.disconnectUser()
                    navController.navigate(RootGraph.AUTH)
                },
                onDeleteClick = {})
        }
    }
}