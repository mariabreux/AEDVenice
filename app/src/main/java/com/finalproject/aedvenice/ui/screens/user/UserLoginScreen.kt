package com.finalproject.aedvenice.ui.screens.user

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun UserLoginScreen(navController: NavHostController, onDismiss: () -> Unit) {
    Surface(color = Color.Transparent) {
        Dialog(onDismissRequest = onDismiss) {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE6E6E6),
                ),
                border = BorderStroke(1.dp, Color(0xFF747171)),
                shape = RoundedCornerShape(1.dp),
                onClick = {
                    onDismiss()
                    navController.navigate("Login")
                }
            ) {
                Text(
                    text = "Login",
                    style = TextStyle(Color.Black)
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun UserLoginScreenPreview() {
    UserLoginScreen(rememberNavController()) {

    }
}