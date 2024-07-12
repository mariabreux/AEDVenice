package com.finalproject.aedvenice.ui.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finalproject.aedvenice.R
import com.finalproject.aedvenice.data.ViewModel
import com.finalproject.aedvenice.data.aed.Report
import com.finalproject.aedvenice.ui.theme.BorderPink
import com.finalproject.aedvenice.ui.theme.DarkPink

@Composable
fun ManageReportScreen(viewModel: ViewModel) {
    var reports by remember { mutableStateOf(emptyList<Report>()) }

    reports = viewModel.getReports()

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
                reports = viewModel.getReports()

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

        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = "Address", modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.padding(15.dp))

            Column(
                modifier = Modifier
                    .border(BorderStroke(1.dp, Color.LightGray), shape = RoundedCornerShape(5.dp))
            ) {
                reports.forEach { report ->
                    Row {
                        Text(
                            text = report.aedId ?: "", modifier = Modifier
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
                                  //  navController.navigate("More info")
                                },
                            textAlign = TextAlign.Center
                        )


                    }
                    Divider()
                }

            }


        }


    }
}