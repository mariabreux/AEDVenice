package com.finalproject.aedvenice.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.finalproject.aedvenice.R
import com.finalproject.aedvenice.data.ViewModel
import com.finalproject.aedvenice.data.aed.Aed
import com.finalproject.aedvenice.data.aed.Report
import com.finalproject.aedvenice.ui.theme.BorderPink
import com.finalproject.aedvenice.ui.theme.DarkPink
import com.finalproject.aedvenice.ui.theme.LightPink
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer

@Composable
fun ManageReportScreen(viewModel: ViewModel) {
    var reports by remember { mutableStateOf(emptyList<Report>()) }
    var isLoading by remember { mutableStateOf(true) }
    var aedId by remember { mutableStateOf<String?>("") }
    val aedState by viewModel.getAedById(aedId ?: "").observeAsState(initial = null)

    LaunchedEffect(Unit) {
        viewModel.getReports { reps ->
            reports = reps
            isLoading = false
        }
    }

    var showDialog by remember { mutableStateOf(false) }
    val onDismiss = { showDialog = false }
    var selectedReport by remember { mutableStateOf<Report?>(null) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(18.dp)
            .fillMaxSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Reports",
                style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.SemiBold),
            )

            Spacer(modifier = Modifier.weight(1f))

            IconButton(onClick = {
                isLoading = true
                viewModel.getReports { reps ->
                    reports = reps
                    isLoading = false
                }

            }) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.refresh),
                    tint = Color.Unspecified,
                    contentDescription = "refresh",
                    modifier = Modifier
                        .size(30.dp)
                )
            }
        }

        Spacer(modifier = Modifier.padding(30.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = "AED ID", modifier = Modifier.weight(1f))
        }

        if (isLoading) {
            ShimmerEffect()
        } else {
            Spacer(modifier = Modifier.padding(15.dp))
        }
        LazyColumn {
            items(reports) { report ->
                aedId = report.aedId
                Column(
                    modifier = Modifier
                        .border(
                            BorderStroke(1.dp, Color.LightGray),
                            shape = RoundedCornerShape(5.dp)
                        )
                ) {
                    Row {
                        Text(
                            text = aedState?.aedBasics?.id?: "",
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 5.dp)
                                .align(Alignment.CenterVertically)
                        )

                        Divider(
                            modifier = Modifier
                                .height(50.dp)
                                .width(1.dp),
                            color = Color.LightGray
                        )

                        Text(
                            text = "More info",
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                            color = DarkPink,
                            textDecoration = TextDecoration.Underline,

                            modifier = Modifier
                                .padding(horizontal = 5.dp)
                                .align(Alignment.CenterVertically)
                                .clickable {
                                    selectedReport = report
                                    showDialog = true
                                },
                            textAlign = TextAlign.Center
                        )
                    }
                    Divider()
                }
            }
        }
    }


    if (showDialog && selectedReport != null) {
        selectedReport?.let {
            MoreInfoScreen(
                onDismiss = { onDismiss() },
                it,
                aedState,
                viewModel,
                context
            )
        }
    }
}

@Composable
fun MoreInfoScreen(
    onDismiss: () -> Unit,
    report: Report,
    aed: Aed?,
    viewModel: ViewModel,
    context: Context
) {
    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(5.dp))
                .height(360.dp)
                .width(310.dp)
                .background(LightPink)
                .border(2.dp, color = DarkPink, shape = RoundedCornerShape(5.dp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.arrow_left),
                        tint = Color.Unspecified,
                        contentDescription = "back",
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Top,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp)
            ) {
                androidx.compose.material.Text(
                    text = aed?.name.toString(),
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.SemiBold),
                )
            }
            Spacer(modifier = Modifier.padding(10.dp))

            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp)
            ) {
                androidx.compose.material.Text(
                    text = "Address: " + aed?.aedBasics?.address,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
                )

                Spacer(modifier = Modifier.padding(10.dp))

                androidx.compose.material.Text(
                    text = "Error: " + report.message.toString(),
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
                )
            }
            Spacer(modifier = Modifier.padding(10.dp))

            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp)
            ) {
                androidx.compose.material.Text(
                    text = "User: " + report.uuid,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
                )
            }

            Spacer(modifier = Modifier.height(25.dp))

            androidx.compose.material3.Button(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkPink,
                ),
                border = BorderStroke(2.dp, BorderPink),
                onClick = {
                    report.uuid?.let { it1 ->
                        viewModel.markAsSpam(it1,
                            {
                                Toast.makeText(context, "Successfully Marked As Spam", Toast.LENGTH_LONG).show()
                                report.id?.let { viewModel.deleteReport(it,  {
                                        Toast.makeText(context, "Report Deleted", Toast.LENGTH_LONG).show()
                                    },
                                    {
                                        Toast.makeText(context, "Error Deleting Report", Toast.LENGTH_LONG).show()
                                    })
                                }
                            },
                            {
                                Toast.makeText(context, "Error Marking As Spam", Toast.LENGTH_LONG).show()
                            },
                            {
                                Toast.makeText(context, "User Was Banned", Toast.LENGTH_LONG).show()

                            },
                            {
                                Toast.makeText(context, "Error Banning User", Toast.LENGTH_LONG).show()
                            }
                        )
                    }
                    onDismiss()
                }
            ) {
                androidx.compose.material.Text(
                    text = "Report as Spam",
                    color = Color.White,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                )
            }

            androidx.compose.material3.Button(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkPink,
                ),
                border = BorderStroke(2.dp, BorderPink),
                onClick = {
                    report.id?.let {
                        viewModel.deleteReport(it,  {
                            Toast.makeText(context, "Report Deleted", Toast.LENGTH_LONG).show()
                        },
                        {
                            Toast.makeText(context, "Error Deleting Report", Toast.LENGTH_LONG).show()
                        })
                    }
                    onDismiss()
                }
            ) {
                androidx.compose.material.Text(
                    text = "Mark as resolved",
                    color = Color.White,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}

@Composable
fun ShimmerEffect(){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        repeat(4){
            ShimmerPlaceholder()
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun ShimmerPlaceholder(){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .placeholder(
                visible = true,
                highlight = PlaceholderHighlight.shimmer(),
                color = Color.Gray,
                shape = RoundedCornerShape(4.dp)
            )
    )
}