package com.finalproject.aedvenice.ui.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.finalproject.aedvenice.data.ViewModel
import com.finalproject.aedvenice.ui.theme.BorderPink
import com.finalproject.aedvenice.ui.theme.DarkPink

@Composable
fun ReportProblemScreen(navController : NavController, viewModel : ViewModel/*TODO: receive Aed Id*/) {

    val id = "HNR2dh19W6xTCirysDVu"

    val aed = viewModel.getAedById(id).observeAsState()
    val context = LocalContext.current

    var reportMessage by remember { mutableStateOf("") }
    var isIssue1Checked by remember { mutableStateOf(false) }
    var isIssue2Checked by remember { mutableStateOf(false) }
    var isOtherChecked by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 25.dp)
        ) {

            Checkbox(
                checked = isIssue1Checked,
                onCheckedChange = { isChecked ->
                    isIssue1Checked = isChecked
                },
                enabled = true
            )
            Text(
                text = "Device is not functioning",
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold),
                fontSize = 18.sp

            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Checkbox(
                checked = isIssue2Checked,
                onCheckedChange = { isChecked ->
                    isIssue2Checked = isChecked
                },
                enabled = true
            )
            Text(
                text = "There is no device in this location",
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold),
                fontSize = 18.sp

            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Checkbox(
                checked = isOtherChecked,
                onCheckedChange = { isChecked ->
                    isOtherChecked = isChecked

                    if (!isOtherChecked) {
                        reportMessage = ""
                    }
                },
                enabled = true
            )
            Text(
                text = "Other",
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold),
                fontSize = 18.sp
            )
        }

        if (isOtherChecked) {
            Column(
                modifier = Modifier
                    .padding(27.dp)
            ) {
                Text(
                    text = "Message:",
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold),
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(top = 10.dp)
                )
                TextField(
                    value = reportMessage,
                    onValueChange = { reportMessage = it },
                    modifier = Modifier
                        .padding(top = 6.dp)
                        .fillMaxWidth()
                        .height(200.dp),
                )
            }
        }

        Spacer(modifier = Modifier.padding(10.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(27.dp)
        ) {

            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkPink,
                ),
                border = BorderStroke(2.dp, BorderPink),
                onClick = {
                    if(isIssue1Checked){
                        reportMessage = "Device is not functioning"
                    } else if(isIssue2Checked){
                        reportMessage = "There is no device in this location"
                    }
                    aed.value?.aedBasics?.geoPoint?.let {
                        viewModel.createReport(
                            id,
                            it,
                            reportMessage,
                            onSuccess = {
                                navController.navigate("Home")
                            },
                            onFailure = {
                                Toast.makeText(
                                    context,
                                    "Error reporting problem",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        )
                    }
                }
            ) {
                Text(
                    text = "Submit",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                )

            }
            Spacer(modifier = Modifier.padding(3.dp))
            Text(
                text = "Cancel",
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                color = DarkPink,
                textDecoration = TextDecoration.Underline,

                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate("Home")
                    },
                textAlign = TextAlign.Center
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ReportProblemScreenPreview() {
    //ReportProblemScreen()
}