package com.finalproject.aedvenice.ui.screens.user

import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.finalproject.aedvenice.data.auth.presentation.SignInViewModel

@Composable
fun Test(viewModel : SignInViewModel = hiltViewModel()) {
    Button(onClick = { viewModel.removeUser() }) {
        Text(text = "Delete Account")
    }
    
    Button(onClick = { viewModel.updatePassword("teste123") }) {
        Text(text = "Update password to teste123")
    }
}