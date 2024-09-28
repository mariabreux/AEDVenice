package com.finalproject.aedvenice.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.finalproject.aedvenice.R
import com.finalproject.aedvenice.data.ViewModel
import com.finalproject.aedvenice.ui.screens.AddEditAedScreen
import com.finalproject.aedvenice.ui.screens.user.ChangePwdScreen
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
    //"Manage Users" to R.drawable.groups,
    "Manage Report" to R.drawable.report,
    "Definitions" to R.drawable.more_vert

)

object NavigationUtils {
    fun navigateToScreen(navController: NavHostController, route: String) {
        navController.navigate(route) {
            popUpTo(navController.graph.startDestinationId)
            launchSingleTop = true
        }
    }
}

@Composable
fun NavigationGraph(navController: NavHostController, viewModel: ViewModel) {
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = "Home") {
        composable("Home") { HomeScreen(navController, viewModel) }
        composable("Manage Aed") { ManageAedScreen(viewModel, navController) }
        composable("How To Act") { HowToActScreen(context, navController) }
        composable("User Login") { LoginScreen(navController, viewModel) }
        composable("Report/{id}") { backStackEntry ->
            // Retrieve the ID from the route
            val id = backStackEntry.arguments?.getString("id")
            if (id != null)
                ReportProblemScreen(navController, viewModel, id)
            //ReportProblemScreen(navController, viewModel)
        }
        composable("Registration") { RegistrationScreen(navController, viewModel) }
        composable("Manage Report") { ManageReportScreen(viewModel) }
        composable("Manage Users") { ManageUsersScreen(viewModel, navController) }
        composable(
            route= "AddEditAed?aedId={aedId}",
            arguments = listOf(navArgument("aedId") {
                defaultValue = ""
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val aedId = backStackEntry.arguments?.getString("aedId")
            aedId?.let {
                AddEditAedScreen(viewModel, navController, it)
            }

        }
        composable("Change Password") { ChangePwdScreen(navController) }
        composable("Test") { Test() }
    }
}