package com.finalproject.aedvenice.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.finalproject.aedvenice.R
import com.finalproject.aedvenice.ui.theme.DarkPink

@Composable
fun AddEditAedScreen(navController: NavHostController) {
    var name by remember { mutableStateOf("") }
    var showDays = remember { mutableStateOf(false) }
    val days = arrayOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
    var selectedDay = remember { mutableStateOf(days[0]) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(top = 30.dp)
                        .weight(7f)
                ) {
                    Text(
                        text = "AED",
                        style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.SemiBold),
                        modifier = Modifier.padding(start = 30.dp)
                    )
                }

                Column(
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier.padding(top = 40.dp)
                ) {
                    IconButton(
                        onClick = { /* TODO: save info */ },
                        modifier = Modifier.size(47.dp)
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.save),
                            contentDescription = "info",
                            tint = Color.Unspecified
                        )
                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.padding(15.dp)) }
        item { formAED(text = "Name", tfValue = name) }
        item { formAED(text = "Address", tfValue = name) }
        item { formAED(text = "Coordinates", tfValue = name) }
        item { formAED(text = "City", tfValue = name) }
        item { formAED(text = "Location", tfValue = name) }
        item { formAED(text = "Telephone", tfValue = name) }
        item { formAED(text = "Note", tfValue = name) }
        item { formAED(text = "Timetable", tfValue = "") }
        item { Spacer(modifier = Modifier.padding(10.dp)) }
        item { Timetable() }
    }
}

@SuppressLint("Range")
@Composable
fun formAED(text: String, tfValue: String, xValue: String = " ", yValue: String = " ") {
    when (text) {
        "Coordinates" -> {
            Row {
                Column(modifier = Modifier.padding(10.dp)) {
                    Row {
                        Text(
                            text = "$text:",
                            style = MaterialTheme.typography.displaySmall.copy(
                                fontWeight = FontWeight.Normal,
                                fontSize = 25.sp
                            )
                        )

                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.info),
                            contentDescription = "info",
                            tint = Color.Unspecified,
                            modifier = Modifier
                                .size(40.dp)
                                .padding(start = 10.dp)
                        )
                    }
                }

                Column(modifier = Modifier.padding(10.dp)) {
                    var x by remember { mutableStateOf(xValue) }
                    var y by remember { mutableStateOf(yValue) }

                    Row {
                        OutlinedTextField(
                            value = x,
                            onValueChange = { x = it },
                            modifier = Modifier
                                .height(45.dp)
                                .weight(1f)
                        )
                        Spacer(modifier = Modifier.padding(3.dp))
                        OutlinedTextField(
                            value = y,
                            onValueChange = { y = it },
                            modifier = Modifier
                                .height(45.dp)
                                .weight(1f)
                        )
                    }
                }
            }
        }

        "Timetable" -> {
            Row {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(
                        text = "$text:",
                        style = MaterialTheme.typography.displaySmall.copy(
                            fontWeight = FontWeight.Normal,
                            fontSize = 25.sp
                        )
                    )
                }
            }
        }

        else -> {
            Row {
                Column(modifier = Modifier.padding(10.dp)) {
                    Row {
                        Text(
                            text = "$text:",
                            style = MaterialTheme.typography.displaySmall.copy(
                                fontWeight = FontWeight.Normal,
                                fontSize = 25.sp
                            )
                        )

                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.info),
                            contentDescription = "info",
                            tint = Color.Unspecified,
                            modifier = Modifier
                                .size(40.dp)
                                .padding(start = 10.dp)
                        )
                    }
                }

                Column(modifier = Modifier.padding(10.dp)) {
                    var tf by remember { mutableStateOf(tfValue) }

                    OutlinedTextField(
                        value = tf,
                        onValueChange = { tf = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(45.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Timetable() {
    val days = arrayOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
    var expanded by remember { mutableStateOf(false) }
    var selectedDay by remember { mutableStateOf(days[0]) }
    var start by remember { mutableStateOf("") }
    var end by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
    ) {
        Box(modifier = Modifier.weight(1.25f)) {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = selectedDay,
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = MaterialTheme.typography.displaySmall.copy(
                        fontWeight = FontWeight.Normal,
                        fontSize = 20.sp
                    ),
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    days.forEach { day ->
                        DropdownMenuItem(
                            text = { Text(text = day) },
                            onClick = {
                                selectedDay = day
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.padding(10.dp))

        OutlinedTextField(
            value = start,
            onValueChange = { start = it },
            modifier = Modifier.weight(1f),
            textStyle = MaterialTheme.typography.displaySmall.copy(
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp
            ),
            placeholder = {
                Text(
                    text = "From:",
                    style = MaterialTheme.typography.displaySmall.copy(
                        fontSize = 15.sp,
                    )
                )
            }

        )
        Spacer(modifier = Modifier.padding(10.dp))
        OutlinedTextField(
            value = end,
            onValueChange = { end = it },
            modifier = Modifier.weight(1f),
            textStyle = MaterialTheme.typography.displaySmall.copy(
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp
            ),
            placeholder = {
                Text(
                    text = "To:",
                    style = MaterialTheme.typography.displaySmall.copy(
                        fontSize = 15.sp,
                    )
                )
            }
        )
    }
    Spacer(modifier = Modifier.padding(5.dp))
    Text(
        text = "Split Service ->",
        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
        textDecoration = TextDecoration.Underline,

        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                showDialog = true
            },
        textAlign = TextAlign.Center
    )

    if (showDialog) {
        SplitService(onDismiss = { showDialog = false }, selectedDay, start, end)

    }
}


@Composable
fun SplitService(
    onDismiss: () -> Unit,
    selectedDay: String,
    initialStart: String,
    initialEnd: String
) {
    var start by remember { mutableStateOf(initialStart) }
    var end by remember { mutableStateOf(initialEnd) }
    var firstEnd by remember { mutableStateOf("") }
    var secondStart by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(5.dp))
                .height(270.dp)
                .width(310.dp)
                .background(Color.White)
                .border(2.dp, color = DarkPink, shape = RoundedCornerShape(5.dp))
                .padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier.fillMaxWidth()
                    .weight(1f),
            ) {
                Text(
                    text = "<- Split Service",
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                    textDecoration = TextDecoration.Underline,

                    modifier = Modifier
                        .clickable {
                            onDismiss()
                        },
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.padding(30.dp))
                Text(
                    text = selectedDay,
                    modifier = Modifier.weight(1f),
                )
            }

            Row{
                OutlinedTextField(
                    value = start,
                    onValueChange = { start = it },
                     modifier = Modifier.weight(1f),
                    textStyle = MaterialTheme.typography.displaySmall.copy(
                        fontWeight = FontWeight.Normal,
                        fontSize = 20.sp
                    ),
                    placeholder = {
                        Text(
                            text = "From:",
                            style = MaterialTheme.typography.displaySmall.copy(
                                fontSize = 15.sp,
                            )
                        )
                    }

                )
                Spacer(modifier = Modifier.padding(10.dp))
                OutlinedTextField(
                    value = firstEnd,
                    onValueChange = { firstEnd = it },
                    modifier = Modifier.weight(1f),
                    textStyle = MaterialTheme.typography.displaySmall.copy(
                        fontWeight = FontWeight.Normal,
                        fontSize = 20.sp
                    ),
                    placeholder = {
                        Text(
                            text = "To:",
                            style = MaterialTheme.typography.displaySmall.copy(
                                fontSize = 15.sp,
                            )
                        )
                    }
                )
            }
            Spacer(modifier = Modifier.padding(20.dp))

            Row{
                OutlinedTextField(
                    value = secondStart,
                    onValueChange = { secondStart = it },
                    modifier = Modifier.weight(1f),
                    textStyle = MaterialTheme.typography.displaySmall.copy(
                        fontWeight = FontWeight.Normal,
                        fontSize = 20.sp
                    ),
                    placeholder = {
                        Text(
                            text = "From:",
                            style = MaterialTheme.typography.displaySmall.copy(
                                fontSize = 15.sp,
                            )
                        )
                    }

                )
                Spacer(modifier = Modifier.padding(10.dp))
                OutlinedTextField(
                    value = end,
                    onValueChange = { end = it },
                    modifier = Modifier.weight(1f),
                    textStyle = MaterialTheme.typography.displaySmall.copy(
                        fontWeight = FontWeight.Normal,
                        fontSize = 20.sp
                    ),
                    placeholder = {
                        Text(
                            text = "To:",
                            style = MaterialTheme.typography.displaySmall.copy(
                                fontSize = 15.sp,
                            )
                        )
                    }
                )
            }


        }

    }
}


@Preview(showBackground = true)
@Composable
fun AddEditAedScreenPreview() {
    AddEditAedScreen(rememberNavController())
}
