package com.finalproject.aedvenice.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
    LazyColumn(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            Column(
                modifier = Modifier.padding(44.dp)
            ) {
                Text(
                    text = "Turn on the equipment using the On/Off button",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(24.dp)
                )
                Spacer(modifier = Modifier.padding(12.dp))
                //Image(painter = , contentDescription = )
            }
            Spacer(modifier = Modifier.padding(24.dp))
        }
       item {
           Column(
               modifier = Modifier.padding(44.dp)
           ) {
               Text(
                   text = "Remove clothing to expose the victim's chest",
                   style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                   modifier = Modifier.padding(24.dp)
               )
               Spacer(modifier = Modifier.padding(12.dp))
               //Image(painter = , contentDescription = )
           }

           Spacer(modifier = Modifier.padding(24.dp))
       }

        item {
            Column(
                modifier = Modifier.padding(44.dp)
            ) {
                Text(
                    text = "Place the adhesive plates on the victim's chest, according to the image on the plates",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(24.dp)
                )
                Spacer(modifier = Modifier.padding(12.dp))
                //Image(painter = , contentDescription = )
            }
            Spacer(modifier = Modifier.padding(24.dp))
        }

        item {
            Column(
                modifier = Modifier.padding(44.dp)
            ) {
                Text(
                    text = "Connect the plates to the AED through the connector",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(24.dp)
                )
                Spacer(modifier = Modifier.padding(12.dp))
                //Image(painter = , contentDescription = )
            }
            Spacer(modifier = Modifier.padding(24.dp))

        }

        item {
            Column(
                modifier = Modifier.padding(44.dp)
            ) {
                Text(
                    text = "Follow the audible or visual instructions of the AED",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(24.dp)
                )
                Spacer(modifier = Modifier.padding(12.dp))
                //Image(painter = , contentDescription = )
            }
            Spacer(modifier = Modifier.padding(24.dp))

        }
        item {
            Column(
                modifier = Modifier.padding(44.dp)
            ) {
                Text(
                    text = "Ensure that no one touches the victim during the analysis of the heart rhythm by the AED",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(24.dp)
                )
                Spacer(modifier = Modifier.padding(12.dp))
                //Image(painter = , contentDescription = )
            }
            Spacer(modifier = Modifier.padding(24.dp))

        }

        item {
            Column(
                modifier = Modifier.padding(44.dp)
            ) {
                Text(
                    text = "Follow the instructions emitted by the AED:",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(24.dp)
                )
                Spacer(modifier = Modifier.padding(8.dp))

                // For shock indicated section
                Text(
                    text = "If the shock is indicated:",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(start = 16.dp)
                )
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = "• Manage the shock",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 32.dp)
                )
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = "• Immediately restart continuous compressions",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 32.dp)
                )
                Spacer(modifier = Modifier.padding(12.dp))

                // For shock not indicated section
                Text(
                    text = "If the shock is not indicated:",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(start = 16.dp)
                )
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = "• Immediately restart compressions",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 32.dp)
                )
                Spacer(modifier = Modifier.padding(24.dp))
            }
        }


        item {
            Column(
                modifier = Modifier.padding(44.dp)
            ) {
                Text(
                    text = "Continue with compressions during 2 minutes. After this time, the AED will restart a new rhythm analysis",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(24.dp)
                )
                Spacer(modifier = Modifier.padding(12.dp))
                //Image(painter = , contentDescription = )
            }
            Spacer(modifier = Modifier.padding(24.dp))

        }

        item {
            Column(
                modifier = Modifier.padding(44.dp)
            ) {
                Text(
                    text = "If you are not alone, switch the compression position with the other person after each analysis (2 minutes), to avoid becoming exhausted",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(24.dp)
                )
                Spacer(modifier = Modifier.padding(12.dp))
                //Image(painter = , contentDescription = )
            }
            Spacer(modifier = Modifier.padding(24.dp))

        }

    }
}


@Preview(showBackground = true)
@Composable
fun HowToActScreenPreview(){
    HowToActScreen(rememberNavController())
}
