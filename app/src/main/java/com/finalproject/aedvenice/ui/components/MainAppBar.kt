package com.finalproject.aedvenice.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.finalproject.aedvenice.data.ViewModel
import com.finalproject.aedvenice.ui.theme.DarkPink
import com.finalproject.aedvenice.ui.theme.LightPink
import com.finalproject.aedvenice.utils.NavigationUtils
import com.finalproject.aedvenice.utils.navBarItems
import com.finalproject.aedvenice.utils.navBarItemsAdmin

@Composable
fun MainAppBar(navController: NavHostController, viewModel: ViewModel) {

    var showLoginButton by remember { mutableStateOf(false) }

    val adminMode = viewModel.adminMode.value

    val appBar: Map<String, Int> = if (!adminMode)
        navBarItems
    else
        navBarItemsAdmin

    Surface(
        elevation = 8.dp,
        modifier = Modifier
            .border(
                border = BorderStroke(1.dp, DarkPink),
                shape = RoundedCornerShape(4.dp)
            )
            .clip(RoundedCornerShape(4.dp))
    ) {
        BottomNavigation(
            backgroundColor = LightPink,
            modifier = Modifier
                .height(70.dp)
                .border(
                    border = BorderStroke(1.dp, DarkPink),
                    shape = RoundedCornerShape(4.dp)
                )
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            appBar.onEachIndexed { index, screen ->
                val isSelected = currentRoute == screen.key

                BottomNavigationItem(
                    modifier = Modifier
                        .padding(vertical = 10.dp),
                    icon = {
                        Icon(
                            painter = painterResource(id = screen.value),
                            contentDescription = null,
                            tint = Color.Unspecified,
                            modifier = Modifier
                                .padding(bottom = if (isSelected) 4.dp else 8.dp)
                                .size(if (isSelected) 48.dp else 32.dp)
                        )
                    },
                    selected = isSelected,
                    onClick = {
                        if (!isSelected /*&& screen.key != "User Login"*/) {
                            NavigationUtils.navigateToScreen(navController, screen.key)

                            navController.navigate(screen.key) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        }
//                        else if (screen.key == "User Login") {
//                            showLoginButton = true
//
//                        }
                    }
                )

//                if (screen.key == "User Login" && showLoginButton) {
//                    DropdownMenu(
//                        expanded = showLoginButton,
//                        onDismissRequest = { showLoginButton = false },
//                    ) {
//                        DropdownMenuItem(
//                            onClick = {
//                            showLoginButton = false
//                            navController.navigate("Login")
//                        },
//                            modifier = Modifier
//                                .background(Color.LightGray)
//                                .border(1.dp, Color.DarkGray, shape = RoundedCornerShape(1.dp))
//
//                        ) {
//                            Text(
//                                text = "Login",
//                                style = TextStyle(Color.Black)
//                            )
//                        }
//                    }
//                }
                if (index < navBarItems.size) {
                    Spacer(
                        modifier = Modifier
                            .height(45.dp)
                            .align(Alignment.CenterVertically)
                            .width(1.dp)
                            .background(DarkPink)
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainAppBarPreview() {
    //MainAppBar(rememberNavController())
}