package com.finalproject.aedvenice.ui.screens.user

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.finalproject.aedvenice.data.ViewModel
import com.finalproject.aedvenice.data.auth.presentation.SignUpViewModel
import com.finalproject.aedvenice.ui.theme.BorderPink
import com.finalproject.aedvenice.ui.theme.DarkPink
import kotlinx.coroutines.launch

@Composable
fun RegistrationScreen(
    navController: NavHostController,
    aedViewModel: ViewModel,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val state = viewModel.signUpState.collectAsState(initial = null)

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
            Text(
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
                    .height(48.dp)
            )
            Spacer(modifier = Modifier.padding(12.dp))
            Text(
                text = "Password:",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
            Spacer(modifier = Modifier.padding(18.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                visualTransformation = PasswordVisualTransformation(),
                textStyle = TextStyle(Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            )
            Spacer(modifier = Modifier.padding(40.dp))
            androidx.compose.material3.Button(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkPink,
                ),
                border = BorderStroke(2.dp, BorderPink),
                onClick = {
                    scope.launch {
                        viewModel.registerUser(email, password)
                    }

                }
            ) {
                Text(
                    text = "Register New User",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                )
            }

            LaunchedEffect(key1 = state.value?.isSuccess) {
                scope.launch {
                    if (state.value?.isSuccess?.isNotEmpty() == true) {
                        val success = state.value?.isSuccess
                        Toast.makeText(context, "$success", Toast.LENGTH_LONG).show()
                        aedViewModel.adminMode.value = true
                        navController.navigate("Manage Users")
                    }
                }
            }

            LaunchedEffect(key1 = state.value?.isError) {
                scope.launch {
                    if (state.value?.isError?.isNotEmpty() == true) {
                        val error = state.value?.isError
                        Toast.makeText(context, "$error", Toast.LENGTH_LONG).show()
                        error?.let { Log.i("ERROR", it) }
                    }
                }
            }
        }
    }
}

