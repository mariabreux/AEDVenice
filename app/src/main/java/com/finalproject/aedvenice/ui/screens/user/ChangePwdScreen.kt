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
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.finalproject.aedvenice.data.auth.presentation.SignInViewModel
import com.finalproject.aedvenice.ui.theme.BorderPink
import com.finalproject.aedvenice.ui.theme.DarkPink

@Composable
fun ChangePwdScreen(
    navController: NavHostController,
    viewModel: SignInViewModel = hiltViewModel()
) {
    var currentPwd by remember { mutableStateOf("") }
    var newPwd by remember { mutableStateOf("") }
    var confirmPwd by remember { mutableStateOf("") }
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
                .padding(50.dp)
        ) {
            Text(
                text = "Current Password:",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
            Spacer(modifier = Modifier.padding(12.dp))
            OutlinedTextField(
                value = currentPwd,
                onValueChange = { currentPwd = it },
                visualTransformation = PasswordVisualTransformation(),
                textStyle = TextStyle(Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            )
            Spacer(modifier = Modifier.padding(18.dp))

            Text(
                text = "New Password:",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
            Spacer(modifier = Modifier.padding(12.dp))
            OutlinedTextField(
                value = newPwd,
                onValueChange = { newPwd = it },
                visualTransformation = PasswordVisualTransformation(),
                textStyle = TextStyle(Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            )
            Spacer(modifier = Modifier.padding(18.dp))
            Text(
                text = "Confirm New Password:",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
            Spacer(modifier = Modifier.padding(12.dp))
            OutlinedTextField(
                value = confirmPwd,
                onValueChange = { confirmPwd = it },
                visualTransformation = PasswordVisualTransformation(),
                textStyle = TextStyle(Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            )
            Spacer(modifier = Modifier.padding(25.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkPink,
                ),
                border = BorderStroke(2.dp, BorderPink),
                onClick = {
                    if(newPwd != "" || confirmPwd != "" || currentPwd != ""){
                        if(newPwd == confirmPwd){
                            val result = viewModel.updatePassword(newPwd, currentPwd)
                            if(result){
                                Toast.makeText(context, "Password updated successfully", Toast.LENGTH_LONG).show()
                                navController.navigate("Manage Aed")
                            }
                            else{
                                Toast.makeText(context, "Error updating password", Toast.LENGTH_LONG).show()
                            }
                        }
                        else{
                            Toast.makeText(context, "The passwords do not match!", Toast.LENGTH_LONG).show()
                        }
                    }
                    else{
                        Toast.makeText(context, "All fields must be filled", Toast.LENGTH_LONG).show()
                    }
                }
            ) {
                Text(
                    text = "Change Password",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}