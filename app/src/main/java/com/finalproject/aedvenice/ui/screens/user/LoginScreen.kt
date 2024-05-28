package com.finalproject.aedvenice.ui.screens.user

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.finalproject.aedvenice.data.auth.presentation.SignInViewModel
import com.finalproject.aedvenice.ui.theme.BorderPink
import com.finalproject.aedvenice.ui.theme.DarkPink
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel : SignInViewModel = hiltViewModel()) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val state = viewModel.signInState.collectAsState(initial = null)

    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(54.dp)
        ) {
            /*Text(
                text = "Email:",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
            )
            Spacer(modifier = Modifier.padding(12.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                textStyle = TextStyle(Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
            )
            Spacer(modifier = Modifier.padding(12.dp))
            Text(
                text = "Password:",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
            Spacer(modifier = Modifier.padding(12.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                visualTransformation = PasswordVisualTransformation(),
                textStyle = TextStyle(Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
            )*/

            Text(
                text = "Enter your credentials for registration",
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp,
                color = Color.Gray
            )

            TextField(
                value = email,
                onValueChange = {
                    email = it
                },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                singleLine = true,
                placeholder = {
                    androidx.compose.material3.Text(text = "Email")
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = password,
                onValueChange = {
                    password = it
                },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                singleLine = true,
                placeholder = {
                    androidx.compose.material3.Text(text = "Password")
                }
            )


            Spacer(modifier = Modifier.padding(40.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkPink,
                ),
                border = BorderStroke(2.dp, BorderPink),
                onClick = {
                    scope.launch {
                        viewModel.loginUser(email, password)
                    }
                    //navController.navigate("Home")
                    //viewModel.adminMode = true
                }
            ) {
                Text(
                    text = "Login",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                )
            }
            Spacer(modifier = Modifier.padding(40.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkPink,
                ),
                border = BorderStroke(2.dp, BorderPink),
                onClick = {
                    navController.navigate("Registration")
                    //viewModel.adminMode = true
                }
            ) {
                Text(
                    text = "Registration",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                )
            }

            LaunchedEffect(key1 = state.value?.isSuccess) {
                scope.launch {
                    if (state.value?.isSuccess?.isNotEmpty() == true){
                        val success = state.value?.isSuccess
                        Toast.makeText(context, "$success", Toast.LENGTH_LONG).show()
                    }
                }
            }

            LaunchedEffect(key1 = state.value?.isError) {
                scope.launch {
                    if (state.value?.isError?.isNotEmpty() == true){
                        val error = state.value?.isError
                        Toast.makeText(context, "$error", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    //LoginScreen(rememberNavController())
}