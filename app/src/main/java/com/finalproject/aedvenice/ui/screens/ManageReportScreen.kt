package com.finalproject.aedvenice.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finalproject.aedvenice.data.ViewModel
import com.finalproject.aedvenice.data.aed.Report

@Composable
fun ManageReportScreen(viewModel: ViewModel) {
    var reports by remember { mutableStateOf(emptyList<Report>()) }

    reports = viewModel.getReports()

    Column {
        Button(
            onClick = {
                reports = viewModel.getReports()
            }
        ) {
            Text(text = "Refresh")
        }

        Text(text = reports.size.toString())

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn {
            itemsIndexed(reports) { _, report ->
                Row() {
                    report.aedId?.let {
                        Text(
                            text = it,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .weight(2f)
                                .padding(5.dp)
                        )
                    }
                }
                ClickableText(
                    text = AnnotatedString("More info"),
                    style = TextStyle(
                        color = Color.DarkGray,
                        textDecoration = TextDecoration.Underline,
                        fontSize = 10.sp
                    ),
                    onClick = { /*TODO*/ }
                )

            }
        }
    }
}