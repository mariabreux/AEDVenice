package com.finalproject.aedvenice.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.finalproject.aedvenice.R
import com.finalproject.aedvenice.data.ViewModel
import com.finalproject.aedvenice.ui.screens.AddEditAedScreen
import com.finalproject.aedvenice.ui.screens.ChangePwdScreen
import com.finalproject.aedvenice.ui.screens.HomeScreen
import com.finalproject.aedvenice.ui.screens.HowToActScreen
import com.finalproject.aedvenice.ui.screens.user.LoginScreen
import com.finalproject.aedvenice.ui.screens.ManageAedScreen
import com.finalproject.aedvenice.ui.screens.ManageReportScreen
import com.finalproject.aedvenice.ui.screens.ManageUsersScreen
import com.finalproject.aedvenice.ui.screens.ReportProblemScreen
import com.finalproject.aedvenice.ui.screens.user.RegistrationScreen
import com.finalproject.aedvenice.ui.screens.user.Test

val navBarItems: Map<String, Int> = mapOf(
    "Home" to R.drawable.home,
    "User Login" to R.drawable.person,
    "How To Act" to R.drawable.question_mark
)

val navBarItemsAdmin: Map<String, Int> = mapOf(
    "Manage Aed" to R.drawable.home,
    "Manage Users" to R.drawable.groups,
    "Manage Report" to R.drawable.report,
    "Definitions" to R.drawable.more_vert

    )

object NavigationUtils{
    fun navigateToScreen(navController: NavHostController, route: String){
        navController.navigate(route){
            popUpTo(navController.graph.startDestinationId)
            launchSingleTop = true
        }
    }
}

@Composable
fun NavigationGraph(navController: NavHostController, viewModel: ViewModel){

    NavHost(navController = navController, startDestination = "Manage Report") {
        composable("Home"){ HomeScreen(navController, viewModel) }
        composable("Manage Aed"){ ManageAedScreen(viewModel, navController) }
        composable("How To Act"){ HowToActScreen(navController) }
        composable("User Login"){LoginScreen(navController, viewModel)}
        composable("Report"){ ReportProblemScreen(navController, viewModel) }
        composable("Registration"){ RegistrationScreen(navController, viewModel) }
        composable("Manage Report"){ ManageReportScreen(viewModel)}
        composable("Manage Users"){ ManageUsersScreen(viewModel, navController)}
        composable("AddEdit Aed"){ AddEditAedScreen(navController) }
        composable("Change Password"){ ChangePwdScreen(navController) }
        composable("Test"){ Test()}
    }
}