package com.finalproject.aedvenice.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun HowToActScreen(navController: NavHostController){
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.padding(44.dp)
        ) {
            Text(
                text = "First Step",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(24.dp)
            )
            Spacer(modifier = Modifier.padding(12.dp))
            //Image(painter = , contentDescription = )
        }
        Spacer(modifier = Modifier.padding(24.dp))
        Column(
            modifier = Modifier.padding(44.dp)
        ) {
            Text(
                text = "Second Step",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(24.dp)
            )
            Spacer(modifier = Modifier.padding(12.dp))
            //Image(painter = , contentDescription = )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HowToActScreenPreview(){
    HowToActScreen(rememberNavController())
}
