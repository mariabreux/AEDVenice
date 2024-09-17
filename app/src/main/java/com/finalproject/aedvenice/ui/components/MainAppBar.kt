package com.finalproject.aedvenice.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.finalproject.aedvenice.data.ViewModel
import com.finalproject.aedvenice.data.auth.presentation.SignInViewModel
import com.finalproject.aedvenice.ui.theme.DarkPink
import com.finalproject.aedvenice.ui.theme.LightPink
import com.finalproject.aedvenice.utils.NavigationUtils
import com.finalproject.aedvenice.utils.navBarItems
import com.finalproject.aedvenice.utils.navBarItemsAdmin

@Composable
fun MainAppBar(navController: NavHostController, viewModel: ViewModel, signInViewModel: SignInViewModel) {
    var expanded by remember { mutableStateOf(false) }

    val adminMode = viewModel.adminMode.value

    val appBar: Map<String, Int> = if (!adminMode) navBarItems else navBarItemsAdmin

    Surface(
        elevation = 8.dp,
        modifier = Modifier
            .border(
                border = BorderStroke(1.dp, DarkPink),
                shape = RoundedCornerShape(4.dp)
            )
            .clip(RoundedCornerShape(4.dp))
    ) {
        Box {
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

                appBar.forEach { screen ->
                    val isSelected = currentRoute == screen.key

                    BottomNavigationItem(
                        modifier = Modifier.padding(vertical = 10.dp),
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
                            if (!isSelected && screen.key != "Definitions") {
                                NavigationUtils.navigateToScreen(navController, screen.key)

                                navController.navigate(screen.key) {
                                    popUpTo(navController.graph.startDestinationId)
                                    launchSingleTop = true
                                }
                            } else if (screen.key == "Definitions") {
                                expanded = !expanded
                            }
                        }
                    )

                    if (screen.key != "Definitions") {
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

            if (expanded) {
                DefinitionsMenu(
                    expanded = expanded,
                    onDismiss = { expanded = false },
                    navController,
                    signInViewModel,
                    viewModel
                )
            }
        }
    }
}

@Composable
fun DefinitionsMenu(expanded: Boolean, onDismiss: () -> Unit, navController: NavHostController, signInViewModel: SignInViewModel, viewModel: ViewModel) {
    val openAlertDialog = remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .padding(top = 70.dp)
            .padding(horizontal = 5.dp)
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onDismiss() },
            modifier = Modifier
                .border(BorderStroke(1.dp, Color.DarkGray), RoundedCornerShape(4.dp))
        ) {
            DropdownMenuItem(onClick = {
                navController.navigate("Change Password")
                onDismiss()
            })
            {
                Text("Change Password")
            }
            Divider(color = Color.DarkGray)
            DropdownMenuItem(
                onClick = {
                    signInViewModel.logoutUser()
                    navController.navigate("Home")
                    viewModel.adminMode.value = false
                    onDismiss()
                }) {
                Text("Log Out")
            }
            Divider(color = Color.DarkGray)
            if(signInViewModel.isUserRemovable()){
                DropdownMenuItem(
                    onClick = {
                        openAlertDialog.value = true
                    }) {
                    Text("Delete Account")
                }
            }
        }
    }
    if(openAlertDialog.value){
        MyAlertDialog(
            navController,
            signInViewModel,
            viewModel,
            openAlertDialog,
            onDismiss
        )
    }
}

@Composable
fun MyAlertDialog(
    navController: NavHostController,
    signInViewModel: SignInViewModel,
    viewModel: ViewModel,
    openAlertDialog: MutableState<Boolean>,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {
            openAlertDialog.value = false
            onDismiss()
        },
        text = {
            Text(
                "Are you sure you want to permanently delete your account?"
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    signInViewModel.removeUser()
                    navController.navigate("Home")
                    viewModel.adminMode.value = false
                    openAlertDialog.value = false
                    onDismiss()
                }
            ) {
                Text("Delete")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    openAlertDialog.value = false
                    onDismiss()
                }
            ) {
                Text("Cancel")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun MainAppBarPreview() {
    val navController = rememberNavController()
    val viewModel = ViewModel()
    //MainAppBar(navController = navController, viewModel = viewModel)
}
