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
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.finalproject.aedvenice.ui.theme.DarkPink
import com.finalproject.aedvenice.ui.theme.LightPink
import com.finalproject.aedvenice.utils.NavigationUtils
import com.finalproject.aedvenice.utils.navBarItemsAdmin

@Composable
fun MainAppBarAdmin(navController: NavHostController){


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
                .height(75.dp)
                .border(
                    border = BorderStroke(1.dp, DarkPink),
                    shape = RoundedCornerShape(4.dp)
                )
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            navBarItemsAdmin.onEachIndexed { index, screen ->
                val isSelected = currentRoute == screen.key

                BottomNavigationItem(
                    modifier = Modifier
                        .padding(vertical = 10.dp),
                    icon = {
                        Icon(
                            painter = painterResource(id = screen.value),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(bottom = if (isSelected) 6.dp else 8.dp)
                                .size(if (isSelected) 132.dp else 120.dp)
                        )
                    },
                    selected = isSelected,
                    onClick = {
                        if (!isSelected) {
                            NavigationUtils.navigateToScreen(navController, screen.key)

                            navController.navigate(screen.key) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        }
                    }
                )
                if(index < navBarItemsAdmin.size){
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



