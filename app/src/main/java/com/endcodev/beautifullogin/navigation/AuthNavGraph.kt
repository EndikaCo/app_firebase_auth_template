package com.endcodev.beautifullogin.navigation

import android.app.AlertDialog
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.endcodev.beautifullogin.data.AuthMessage
import com.endcodev.beautifullogin.domain.DialogErrorUiState
import com.endcodev.beautifullogin.presentation.AuthViewModel
import com.endcodev.beautifullogin.presentation.ui.screens.LoginScreen
import com.endcodev.beautifullogin.presentation.ui.screens.ResetPasswordScreen
import com.endcodev.beautifullogin.presentation.ui.screens.SignUpScreen
import com.endcodev.beautifullogin.presentation.ui.screens.WaitingScreen

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun NavGraphBuilder.authNavGraph(navController: NavHostController) {

    navigation(
        route = RootGraph.AUTH,
        startDestination = AuthGraph.LOGIN.route
    ) {

        composable(route = AuthGraph.LOGIN.route) {
            val viewModel = it.sharedViewModel<AuthViewModel>(navController)
            val uiState by viewModel.uiState.collectAsState()
            val context = LocalContext.current

            LoginScreen(
                uiState = uiState,
                onAuthClick = {
                    viewModel.mailPassLogin(onResult = { error ->
                        if (error == AuthMessage.OK.error) {
                            navController.popBackStack() // clear nav history
                            navController.navigate(RootGraph.HOME)
                        } else {
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
                onUserNameChanged = { userName ->
                    viewModel.updateSignUp(userName = userName)
                }
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

fun showDialog(context: Context, title: String, message: String) {
    val builder = AlertDialog.Builder(context)
    builder.setTitle(title)
    builder.setMessage(message)
    builder.setPositiveButton("OK") { dialog, _ ->
        dialog.dismiss()
    }
    val alertDialog = builder.create()
    alertDialog.show()
}

fun getError(errorNum: Int): DialogErrorUiState {

    val title = "Error $errorNum"
    val description : String = when (errorNum) {
        AuthMessage.MailVerificationError.error -> "Mail Verification Error"
        AuthMessage.ErrorMailOrPass.error -> "Error Mail Or Pass"
        AuthMessage.ErrorSendingMail.error -> "Error Sending Mail"
        AuthMessage.ErrorCreatingAccount.error -> "Error Creating Account"
        AuthMessage.ErrorMailInUse.error -> "Error Mail already in use"
        else -> { "Undefined Error" }
    }
    return DialogErrorUiState(true, title, description, "Ok")
}

sealed class AuthGraph(val route: String) {
    data object LOGIN : AuthGraph(route = "LOGIN")
    data object SIGNUP : AuthGraph(route = "SIGN")
    data object RESET : AuthGraph(route = "RESET")
    data object WAITING : AuthGraph(route = "WAITING")
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController): T {
    val navGraphRoute = destination.parent?.route ?: return viewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return viewModel(parentEntry)
}