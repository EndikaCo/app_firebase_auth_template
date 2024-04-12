package com.endcodev.beautifullogin.presentation.navigation

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.endcodev.beautifullogin.presentation.ui.screens.HomeScreen
import com.endcodev.beautifullogin.presentation.ui.screens.ProfileScreen
import com.endcodev.beautifullogin.presentation.viewmodel.HomeViewModel
import kotlin.system.exitProcess

sealed class HomeGraph {
    data object START : AuthGraph(route = "START")
    data object PROFILE : AuthGraph(route = "PROFILE")
}

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

            val activity = (LocalContext.current as? Activity)
            BackHandler(onBack = {
                exitApp(activity)
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
                onDeleteClick = {viewModel.deleteAccount()},
                onSaveButton = {viewModel.saveNewInfo() }, //todo
                goBackClick = { navController.popBackStack() },
                onMailChanged = { viewModel.changeMail(it) },
                onNameChanged = { viewModel.changeUserName(it) },
                onEditModeClick = { viewModel.changeEditMode() }
            )
        }
    }
}

fun exitApp(activity : Activity? = null){
    activity?.finish()
    exitProcess(0)
}