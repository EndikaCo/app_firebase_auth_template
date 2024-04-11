package com.endcodev.beautifullogin.presentation.navigation

import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.endcodev.beautifullogin.data.AuthMessage
import com.endcodev.beautifullogin.presentation.ui.screens.MailLoginScreen
import com.endcodev.beautifullogin.presentation.ui.screens.ResetPasswordScreen
import com.endcodev.beautifullogin.presentation.ui.screens.SignUpScreen
import com.endcodev.beautifullogin.presentation.ui.screens.WaitingScreen
import com.endcodev.beautifullogin.presentation.utils.getError
import com.endcodev.beautifullogin.presentation.utils.sharedViewModel
import com.endcodev.beautifullogin.presentation.utils.showDialog
import com.endcodev.beautifullogin.presentation.viewmodel.AuthViewModel

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun NavGraphBuilder.authNavGraph(navController: NavHostController) {

    navigation(
        route = RootGraph.AUTH,
        startDestination = AuthGraph.LOGIN.route
    ) {

        composable(route = AuthGraph.LOGIN.route) { it ->
            val viewModel = it.sharedViewModel<AuthViewModel>(navController)
            val uiState by viewModel.uiState.collectAsState()
            val isLoggedIn by viewModel.isLoggedIn.collectAsState()

            if (isLoggedIn) {
                navController.popBackStack() // clear nav history
                navController.navigate(RootGraph.HOME)
            }

            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartActivityForResult(),
                onResult = { result -> viewModel.gLoginInit(result) }
            )
            val context = LocalContext.current

            MailLoginScreen(
                uiState = uiState,
                onAuthClick = {
                    viewModel.mailPassLogin(onResult = { error ->
                        if (error != AuthMessage.OK.error) {
                            val err = getError(error)
                            showDialog(
                                context,
                                err.title,
                                err.message
                            )
                        }
                    })
                },
                onSignUpClick = { navController.navigate(AuthGraph.SIGNUP.route) },
                onResetClick = { navController.navigate(AuthGraph.RESET.route) },
                onEmailChanged = { email -> viewModel.updateLogin(email, uiState.password) },
                onPassChanged = { pass -> viewModel.updateLogin(uiState.email, pass) },
                onGoogleClick = { viewModel.googleLogin(context, launcher) }
            )
        }

        composable(route = AuthGraph.SIGNUP.route) {

            val viewModel = it.sharedViewModel<AuthViewModel>(navController)
            val uiState by viewModel.uiState.collectAsState()
            val context = LocalContext.current

            SignUpScreen(
                uiState,
                onAuthButtonClick = {
                    viewModel.createUser { error ->
                        if (error == AuthMessage.OK.error)
                            navController.navigate(AuthGraph.WAITING.route)
                        else {
                            val err = getError(error)
                            showDialog(
                                context,
                                err.title,
                                err.message
                            )
                        }
                    }
                },
                onLogInClick = { navController.navigate(RootGraph.AUTH) },
                onEmailChanged = { email -> viewModel.updateSignUp(email = email) },
                onPassChanged = { password -> viewModel.updateSignUp(pass = password) },
                onUserNameChanged = { userName -> viewModel.updateSignUp(userName = userName) },
                onCheckedTerms = { terms -> viewModel.updateSignUp(terms = terms) }
            )
        }

        composable(route = AuthGraph.RESET.route) {
            val viewModel = it.sharedViewModel<AuthViewModel>(navController)
            val uiState by viewModel.uiState.collectAsState()

            ResetPasswordScreen(
                email = uiState.email,
                isLoginEnabled = uiState.isAuthButtonEnabled,
                onEmailChanged = { email -> viewModel.updateReset(email) },
                onAuthClick = { navController.navigate(RootGraph.AUTH) },
                onSignUpClick = { navController.navigate(AuthGraph.SIGNUP.route) },
                onLogInClick = { navController.navigate(RootGraph.AUTH) }
            )
        }

        composable(route = AuthGraph.WAITING.route) {
            val viewModel = it.sharedViewModel<AuthViewModel>(navController)
            val uiState by viewModel.uiState.collectAsState()
            viewModel.listenerMailVerification { navController.navigate(RootGraph.AUTH) }
            WaitingScreen(uiState)
        }
    }
}

sealed class AuthGraph(val route: String) {
    data object LOGIN : AuthGraph(route = "LOGIN")
    data object SIGNUP : AuthGraph(route = "SIGN")
    data object RESET : AuthGraph(route = "RESET")
    data object WAITING : AuthGraph(route = "WAITING")
}
