package com.finalproject.aedvenice.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.finalproject.aedvenice.R
import com.finalproject.aedvenice.data.ViewModel
import com.finalproject.aedvenice.data.aed.Aed
import com.finalproject.aedvenice.data.aed.AedBasics
import com.finalproject.aedvenice.data.aed.GeoPoint
import com.finalproject.aedvenice.ui.theme.DarkPink

@Composable
fun AddEditAedScreen(viewModel: ViewModel, navController: NavHostController, aedId: String) {

    val aedState by viewModel.getAedById(aedId).observeAsState()

    var name by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var coordinates by remember { mutableStateOf(GeoPoint()) }
    var telephone by remember { mutableStateOf("") } //TODO: Later change to telephone list?
    val days = arrayOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
    var timetableEntries = remember { mutableStateListOf<TimetableEntry>() }

    val context = LocalContext.current

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
                        onClick = {
                            val newAed = Aed(
                                AedBasics(
                                    null,
                                    address,
                                    coordinates,
                                    note
                                ),
                                name,
                                city,
                                location,
                                convertTimetableToString(timetableEntries),
                                listOf("123", "456") //TODO: pass telephone
                            )
                            if (aedId.isNotEmpty()) { //we are in editable mode
                                //TODO: viewModel.updateAed()

                            } else { //we are in add mode
                                viewModel.createAed(
                                    newAed,
                                    onSuccess = {
                                        Toast.makeText(
                                            context,
                                            "Aed created successfully",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    },
                                    onFailure = {
                                        Toast.makeText(
                                            context,
                                            "Error creating new Aed",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                )
                            }

                            navController.navigate("Manage Aed")
                        },
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
        if (aedId.isNotEmpty()) {
            item {
                name =
                    aedState?.name?.let { formAEDString(text = "Name", tfValue = it) }.toString()
            }
            item {
                address =
                    aedState?.aedBasics?.address?.let {
                        formAEDString(
                            text = "Address",
                            tfValue = it
                        )
                    }
                        .toString()
            }
            item {
                city =
                    aedState?.city?.let { formAEDString(text = "City", tfValue = it) }.toString()
            }
            item {
                location =
                    aedState?.location?.let { formAEDString(text = "Location", tfValue = it) }
                        .toString()
            }
            item {
                telephone = aedState?.phoneNumber?.forEach { num ->
                    formAEDString(
                        text = "Telephone",
                        tfValue = num
                    )
                }.toString()

            }
            item {
                note =
                    aedState?.aedBasics?.notes?.let { formAEDString(text = "Note", tfValue = it) }
                        .toString()
            }
        } else {
            item { name = formAEDString(text = "Name", tfValue = name) }
            item { address = formAEDString(text = "Address", tfValue = address) }
            item { city = formAEDString(text = "City", tfValue = city) }
            item { location = formAEDString(text = "Location", tfValue = location) }
            item { telephone = formAEDString(text = "Telephone", tfValue = telephone) }
            item { note = formAEDString(text = "Note", tfValue = note) }
            item { coordinates = formAEDGeo(text = "Coordinates") }

        }

        item { FormAED(text = "Timetable") }
        item { Spacer(modifier = Modifier.padding(10.dp)) }
        item { Timetable(timetableEntries = timetableEntries) }
    }
}

@SuppressLint("Range")
@Composable
fun FormAED(text: String) {
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

@SuppressLint("Range")
@Composable
fun formAEDString(
    text: String,
    tfValue: String,
): String {
    var tf by remember { mutableStateOf(tfValue) }

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

            OutlinedTextField(
                value = tf,
                onValueChange = { tf = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
            )
        }
    }

    return tf
}

@SuppressLint("Range")
@Composable
fun formAEDGeo(
    text: String,
    xValue: String = "",
    yValue: String = ""
): GeoPoint {
    var x by remember { mutableStateOf(xValue) }
    var y by remember { mutableStateOf(yValue) }

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

    return GeoPoint(x.toDoubleOrNull(), y.toDoubleOrNull())
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Timetable(timetableEntries: MutableList<TimetableEntry>) {
    val days = arrayOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
    var showDialog by remember { mutableStateOf(false) }
    var selectedEntry by remember { mutableStateOf(TimetableEntry()) }



    Column {
        timetableEntries.forEachIndexed { index, entry ->
            var expanded by remember { mutableStateOf(false) }

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
                            value = entry.day,
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
                                        timetableEntries[index] =
                                            timetableEntries[index].copy(day = day)

                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.padding(10.dp))

                OutlinedTextField(
                    value = entry.startTime,
                    onValueChange = { newStartTime ->
                        timetableEntries[index] =
                            timetableEntries[index].copy(startTime = newStartTime)


                    },
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
                    value = entry.endTime,
                    onValueChange = { newEndTime ->
                        timetableEntries[index] = timetableEntries[index].copy(endTime = newEndTime)

                    },
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
                        selectedEntry = entry
                        showDialog = true
                    },
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.padding(5.dp))

        }
        IconButton(onClick = {
            timetableEntries.add(TimetableEntry())


        }) {
            Icon(
                imageVector = Icons.Default.AddCircle,
                contentDescription = "add",
                tint = Color.LightGray
            )
        }

        if (showDialog) {
            SplitService(
                onDismiss = { showDialog = false },
                selectedDay = selectedEntry.day,
                initialStart = selectedEntry.startTime,
                initialEnd = selectedEntry.endTime,
                timetableEntry = selectedEntry
            )
        }

    }

}

data class TimetableEntry(
    var day: String = "Mon",
    var startTime: String = "",
    var endTime: String = "",
    var firstEnd: String = "",
    var secondStart: String = ""
)

fun convertTimetableToString(timetableEntries: List<TimetableEntry>): String { //TODO: probably convert to json when the collection changes
    return timetableEntries.joinToString(", ") { entry ->
        if (entry.firstEnd != "" && entry.secondStart != "") {
            "${entry.day}: ${entry.startTime}-${entry.firstEnd}/${entry.secondStart}-${entry.endTime}"
        } else {
            "${entry.day}: ${entry.startTime}-${entry.endTime}"

        }
    }
}

@Composable
fun SplitService(
    onDismiss: () -> Unit,
    selectedDay: String,
    initialStart: String,
    initialEnd: String,
    timetableEntry: TimetableEntry
) {
    var start by remember { mutableStateOf(initialStart) }
    var end by remember { mutableStateOf(initialEnd) }
    var firstEnd by remember { mutableStateOf(timetableEntry.firstEnd) }
    var secondStart by remember { mutableStateOf(timetableEntry.secondStart) }

    Dialog(
        onDismissRequest = {
            timetableEntry.firstEnd = firstEnd
            timetableEntry.secondStart = secondStart
            onDismiss()
        }
    ) {
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
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            ) {
                Text(
                    text = "<- Split Service",
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                    textDecoration = TextDecoration.Underline,

                    modifier = Modifier
                        .clickable {
                            timetableEntry.firstEnd = firstEnd
                            timetableEntry.secondStart = secondStart
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

            Row {
                OutlinedTextField(
                    value = start,
                    onValueChange = { },
                    modifier = Modifier.weight(1f),
                    textStyle = MaterialTheme.typography.displaySmall.copy(
                        fontWeight = FontWeight.Normal,
                        fontSize = 20.sp
                    )

                )
                Spacer(modifier = Modifier.padding(10.dp))
                OutlinedTextField(
                    value = firstEnd,
                    onValueChange = { newFirstEnd ->
                        firstEnd = newFirstEnd
                    },
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

            Row {
                OutlinedTextField(
                    value = secondStart,
                    onValueChange = { newSecondStart ->
                        secondStart = newSecondStart
                    },
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
                    onValueChange = { },
                    modifier = Modifier.weight(1f),
                    textStyle = MaterialTheme.typography.displaySmall.copy(
                        fontWeight = FontWeight.Normal,
                        fontSize = 20.sp
                    )
                )
            }


        }

    }
}


@Preview(showBackground = true)
@Composable
fun AddEditAedScreenPreview() {
    //AddEditAedScreen(rememberNavController())
}
