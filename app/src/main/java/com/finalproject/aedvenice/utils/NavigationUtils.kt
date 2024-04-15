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
import com.finalproject.aedvenice.ui.screens.HomeScreen
import com.finalproject.aedvenice.ui.screens.HowToActScreen
import com.finalproject.aedvenice.ui.screens.LoginScreen
import com.finalproject.aedvenice.ui.screens.ManageAedScreen
import com.finalproject.aedvenice.ui.screens.ReportProblemScreen
import com.finalproject.aedvenice.ui.screens.UserLoginScreen

val navBarItems: Map<String, Int> = mapOf(
    "Home" to R.drawable.home,
    "User Login" to R.drawable.person,
    "How To Act" to R.drawable.question_mark
)

val navBarItemsAdmin: Map<String, Int> = mapOf(
    "Home" to R.drawable.home,
    "Manage Users" to R.drawable.groups,
    "Report Problem" to R.drawable.report,
    "More" to R.drawable.more_vert

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
fun NavigationGraph(navController: NavHostController){
    var showDialog by remember { mutableStateOf(false) }

    NavHost(navController = navController, startDestination = "How To Act") {
       // composable("Home"){ HomeScreen(navController)}
        composable("Home"){ ManageAedScreen() }
        composable("How To Act"){ HowToActScreen(navController)}
        composable("User Login"){ UserLoginScreen(navController, onDismiss = {showDialog = true})}
        composable("Login"){LoginScreen(navController)}

    }
}