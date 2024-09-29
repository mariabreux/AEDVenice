package com.finalproject.aedvenice.ui.screens

import android.annotation.SuppressLint
import android.content.Context
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.finalproject.aedvenice.R
import com.finalproject.aedvenice.data.ViewModel
import com.finalproject.aedvenice.data.aed.Aed
import com.finalproject.aedvenice.data.aed.AedBasics
import com.finalproject.aedvenice.data.aed.GeoPoint
import com.finalproject.aedvenice.ui.theme.DarkPink
import com.google.gson.Gson
import com.google.gson.JsonObject
import java.io.BufferedReader
import java.io.InputStreamReader

@SuppressLint("UnrememberedMutableState")
@Composable
fun AddEditAedScreen(viewModel: ViewModel, navController: NavHostController, aedId: String) {

    val aedState by viewModel.getAedById(aedId).observeAsState()

    var name by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var coordinates by remember { mutableStateOf(GeoPoint()) }
    var telephone = remember { mutableStateListOf<TelephoneEntry>() }
    var timetableEntries = remember { mutableStateListOf<TimetableEntry>() }
    var phoneNum by remember {
        mutableStateOf("")
    }
    var time by remember {
        mutableStateOf("")
    }
    var textInfo by remember {
        mutableStateOf("")
    }


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
                                time,
                                phoneNum
                            )
                            if (aedId.isNotEmpty()) { //we are in editable mode
                                aedState?.let {
                                    viewModel.updateAed(
                                        aedId,
                                        it.copy(
                                            aedBasics = AedBasics(
                                                id = aedId,
                                                address = address,
                                                geoPoint = coordinates,
                                                notes = note
                                            ),
                                            name = name,
                                            city = city,
                                            location = location,
                                            timetable = time,
                                            phoneNumber = phoneNum
                                        ),
                                        onSuccess = {
                                            Toast.makeText(
                                                context,
                                                "Aed updated successfully",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        },
                                        onFailure = {
                                            Toast.makeText(
                                                context,
                                                "Error updating new Aed",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        })
                                }

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
                textInfo = readTooltipsInfo(context)[0]
                name =
                    aedState?.name?.let { formAEDString(text = "Name", tfValue = it, textInfo) }
                        .toString()
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
                aedState?.phoneNumber.let {
                    if (it != null) {
                        telephone = EditableTelForm(phoneNumber = it)
                        phoneNum = convertTelephoneToString(telephone)
                    }
                }


            }
            item {
                note =
                    aedState?.aedBasics?.notes?.let { formAEDString(text = "Note", tfValue = it) }
                        .toString()
            }
            item { FormAED(text = "Timetable") }
            item { Spacer(modifier = Modifier.padding(10.dp)) }
            item {
                aedState?.timetable.let {
                    if (it != null) {
                        timetableEntries = EditableTimeTableForm(timetable = it)
                        time = convertTimetableToString(timetableEntries)
                    } else{
                        timetableEntries = EditableTimeTableForm(timetable = "")
                        time = convertTimetableToString(timetableEntries)
                    }
                }
            }
        } else {
            item {
                textInfo = readTooltipsInfo(context)[0]
                name = formAEDString(text = "Name", tfValue = name, textInfo)
            }
            item {
                textInfo = readTooltipsInfo(context)[1]
                address = formAEDString(text = "Address", tfValue = address, textInfo)
            }
            item {
                textInfo = readTooltipsInfo(context)[2]
                city = formAEDString(text = "City", tfValue = city, textInfo)
            }
            item {
                textInfo = readTooltipsInfo(context)[3]
                location = formAEDString(text = "Location", tfValue = location, textInfo)
            }
            item {
                textInfo = readTooltipsInfo(context)[4]
                var entries: SnapshotStateList<TelephoneEntry> = FormTelephone(telephone, textInfo)
                phoneNum = convertTelephoneToString(entries)
            }
            item {
                textInfo = readTooltipsInfo(context)[5]
                note = formAEDString(text = "Note", tfValue = note, textInfo)
            }
            item {
                textInfo = readTooltipsInfo(context)[6]
                coordinates = formAEDGeo(text = "Coordinates", textInfo)
            }
            item { FormAED(text = "Timetable") }
            item { Spacer(modifier = Modifier.padding(10.dp)) }
            item {
                var table: MutableList<TimetableEntry> = Timetable(timetableEntries)
                time = convertTimetableToString(table)
            }
        }


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
    tooltip: String? = null,
    showIconButton: Boolean = false,
    onIconButtonClick: (() -> Unit)? = null,
    onRemoveIcon: (() -> Unit)? = null,
    onValueChange: ((String) -> Unit)? = null
): String {
    var tf by remember { mutableStateOf(tfValue) }

    var showTooltip by remember {
        mutableStateOf(false)
    }

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

                Box(
                    modifier = Modifier
                        .padding(start = 3.dp)
                        .clickable { showTooltip = !showTooltip }
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.info),
                        contentDescription = "info",
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .size(40.dp)
                            .padding(start = 10.dp)
                    )

                    if (showTooltip) {
                        Popup(
                            alignment = Alignment.TopStart,
                            offset = IntOffset(
                                -150,
                                -100
                            ),
                            onDismissRequest = { showTooltip = false },
                            properties = PopupProperties(focusable = false)
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(Color.White, shape = MaterialTheme.shapes.small)
                                    .border(1.dp, DarkPink, shape = MaterialTheme.shapes.small)
                                    .padding(8.dp)
                            ) {
                                Text(
                                    text = "$tooltip",
                                    color = Color.Black,
                                    fontSize = 14.sp,
                                )
                            }
                        }
                    }
                }

            }
        }


        Row(modifier = Modifier.padding(10.dp)) {

            OutlinedTextField(
                value = tf,
                onValueChange = {
                    tf = it
                    if (onValueChange != null) {
                        onValueChange(it)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .weight(5f)
            )

            if (showIconButton && text == "Telephone") {
                Column(
                    verticalArrangement = Arrangement.spacedBy(-(15).dp),
                    modifier = Modifier
                        .weight(1f)
                        .offset(0.dp, -(15).dp)

                ) {
                    IconButton(
                        onClick = { onIconButtonClick?.invoke() },
                    ) {
                        Icon(
                            imageVector = Icons.Default.AddCircle,
                            contentDescription = "add",
                            tint = DarkPink,
                        )
                    }
                    IconButton(onClick = {
                        onRemoveIcon?.invoke()
                    }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "delete",
                            tint = DarkPink
                        )
                    }
                }

            }
        }

    }

    return tf
}

@SuppressLint("Range")
@Composable
fun formAEDGeo(
    text: String,
    tooltip: String? = null,
    xValue: String = "",
    yValue: String = ""
): GeoPoint {
    var x by remember { mutableStateOf(xValue) }
    var y by remember { mutableStateOf(yValue) }
    var showTooltip by remember { mutableStateOf(false) }


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

                Box(
                    modifier = Modifier
                        .padding(start = 3.dp)
                        .clickable { showTooltip = !showTooltip }
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.info),
                        contentDescription = "info",
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .size(40.dp)
                            .padding(start = 10.dp)
                    )

                    if (showTooltip) {
                        Popup(
                            alignment = Alignment.TopStart,
                            offset = IntOffset(
                                -150,
                                -100
                            ),
                            onDismissRequest = { showTooltip = false },
                            properties = PopupProperties(focusable = false)
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(Color.White, shape = MaterialTheme.shapes.small)
                                    .border(1.dp, DarkPink, shape = MaterialTheme.shapes.small)
                                    .padding(8.dp)
                            ) {
                                Text(
                                    text = "$tooltip",
                                    color = Color.Black,
                                    fontSize = 14.sp,
                                )
                            }
                        }
                    }
                }
            }
        }

        Column(modifier = Modifier.padding(10.dp)) {

            Row {
                OutlinedTextField(
                    value = x,
                    onValueChange = { x = it },
                    modifier = Modifier
                        .height(47.dp)
                        .weight(1f)
                )
                Spacer(modifier = Modifier.padding(3.dp))
                OutlinedTextField(
                    value = y,
                    onValueChange = { y = it },
                    modifier = Modifier
                        .height(47.dp)
                        .weight(1f)
                )
            }
        }
    }

    return GeoPoint(x.toDoubleOrNull(), y.toDoubleOrNull())
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Timetable(timetableEntries: MutableList<TimetableEntry>): MutableList<TimetableEntry> {
    val days = arrayOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
    var showDialog by remember { mutableStateOf(false) }
    var selectedEntry by remember { mutableStateOf(TimetableEntry()) }
    var selectedEntryRemove by remember { mutableStateOf(TimetableEntry()) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        timetableEntries.forEachIndexed { index, entry ->
            var expanded by remember { mutableStateOf(false) }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp)
            ) {
                Box(modifier = Modifier.weight(2.15f)) {
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        OutlinedTextField(
                            value = entry.day.value,
                            onValueChange = {},
                            readOnly = true,
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = MaterialTheme.typography.displaySmall.copy(
                                fontWeight = FontWeight.Normal,
                                fontSize = 15.sp
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

                                        timetableEntries[index].day.value = day

                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.padding(10.dp))

                OutlinedTextField(
                    value = entry.startTime.value,
                    onValueChange = { newStartTime ->
                        timetableEntries[index].startTime.value = newStartTime


                    },
                    modifier = Modifier.weight(1f),
                    textStyle = MaterialTheme.typography.displaySmall.copy(
                        fontWeight = FontWeight.Normal,
                        fontSize = 15.sp
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
                    value = entry.endTime.value,
                    onValueChange = { newEndTime ->
                        timetableEntries[index].endTime.value = newEndTime

                    },
                    modifier = Modifier.weight(1f),
                    textStyle = MaterialTheme.typography.displaySmall.copy(
                        fontWeight = FontWeight.Normal,
                        fontSize = 15.sp
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

            Spacer(modifier = Modifier.padding(15.dp))
            selectedEntryRemove = entry

        }
        Row {
            IconButton(onClick = {
                timetableEntries.add(TimetableEntry())
            }
            ) {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = "add",
                    tint = DarkPink
                )
            }
            IconButton(onClick = {
                timetableEntries.remove(selectedEntryRemove)
            }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "delete",
                    tint = DarkPink
                )
            }
        }


        if (showDialog) {
            SplitService(
                onDismiss = { showDialog = false },
                selectedDay = selectedEntry.day.value,
                initialStart = selectedEntry.startTime.value,
                initialEnd = selectedEntry.endTime.value,
                timetableEntry = selectedEntry
            )
        }

    }
    return timetableEntries.toMutableList()

}

data class TimetableEntry(
    var day: MutableState<String> = mutableStateOf("Monday"),
    var startTime: MutableState<String> = mutableStateOf(""),
    var endTime: MutableState<String> = mutableStateOf(""),
    var firstEnd: MutableState<String> = mutableStateOf(""),
    var secondStart: MutableState<String> = mutableStateOf(""),
)

fun convertTimetableToString(timetableEntries: List<TimetableEntry>): String {
    val resultMap = timetableEntries.associate { entry ->
        val hours = if (entry.firstEnd.value.isNotEmpty() && entry.secondStart.value.isNotEmpty()) {
            "${entry.startTime.value}-${entry.firstEnd.value} / ${entry.secondStart.value}-${entry.endTime.value}"
        } else {
            "${entry.startTime.value}-${entry.endTime.value}"
        }
        entry.day.value to hours
    }

    return resultMap.entries.joinToString(prefix = "{", postfix = "}") { (day, hours) ->
        "'$day': '$hours'"
    }
}

@Composable
fun EditableTimeTableForm(timetable: String): SnapshotStateList<TimetableEntry> {
    val timetableEntries = remember {
        if (timetable.isEmpty()) {
            mutableStateListOf<TimetableEntry>()
        } else {
            val gson = Gson()
            val timetableMap: JsonObject = gson.fromJson(timetable, JsonObject::class.java)

            timetableMap.entrySet().map { (day, times) ->
                val timeParts = times.asString.split(" / ")
                val firstTimeParts =
                    timeParts.getOrNull(0)?.split("-")?.map { it.trim() } ?: listOf("", "")

                val secondTimeParts =
                    timeParts.getOrNull(1)?.split("-")?.map { it.trim() } ?: listOf("", "")
                if (timeParts.size > 1) {
                    TimetableEntry(
                        day = mutableStateOf(day),
                        startTime = mutableStateOf(firstTimeParts.getOrNull(0) ?: ""),
                        endTime = mutableStateOf(secondTimeParts.getOrNull(1) ?: ""),
                        firstEnd = mutableStateOf(firstTimeParts.getOrNull(1) ?: ""),
                        secondStart = mutableStateOf(secondTimeParts.getOrNull(0) ?: "")
                    )

                } else {
                    TimetableEntry(
                        day = mutableStateOf(day),
                        startTime = mutableStateOf(firstTimeParts.getOrNull(0) ?: ""),
                        endTime = mutableStateOf(firstTimeParts.getOrNull(1) ?: ""),
                    )
                }
            }.toMutableStateList()
        }
    }

    Column {
        Timetable(timetableEntries)
    }
    return timetableEntries
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
                    value = firstEnd.value,
                    onValueChange = { newFirstEnd ->
                        firstEnd.value = newFirstEnd
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
                    value = secondStart.value,
                    onValueChange = { newSecondStart ->
                        secondStart.value = newSecondStart
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

data class TelephoneEntry(
    // var telNum: String = ""
    var telNum: MutableState<String>
)

fun convertTelephoneToString(telephoneEntries: List<TelephoneEntry>): String {
    return telephoneEntries.joinToString("; ") { entry ->
        entry.telNum.value
    }
}

@Composable
fun FormTelephone(
    telephoneEntries: MutableList<TelephoneEntry>,
    tooltip: String? = null
): SnapshotStateList<TelephoneEntry> {
    var showTooltip by remember {
        mutableStateOf(false)
    }

    Column {
        if (telephoneEntries.isEmpty()) {
            Row(
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Telephone",
                    style = MaterialTheme.typography.displaySmall.copy(
                        fontWeight = FontWeight.Normal,
                        fontSize = 25.sp
                    )
                )
                Box(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .clickable { showTooltip = !showTooltip }
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.info),
                        contentDescription = "info",
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .size(40.dp)
                            .padding(start = 10.dp)
                    )

                    if (showTooltip) {
                        Popup(
                            alignment = Alignment.TopStart,
                            offset = IntOffset(
                                -150,
                                -100
                            ),
                            onDismissRequest = { showTooltip = false },
                            properties = PopupProperties(focusable = false)
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(Color.White, shape = MaterialTheme.shapes.small)
                                    .border(1.dp, DarkPink, shape = MaterialTheme.shapes.small)
                                    .padding(8.dp)
                            ) {
                                Text(
                                    text = "$tooltip",
                                    color = Color.Black,
                                    fontSize = 14.sp,
                                )
                            }
                        }
                    }
                }

                IconButton(onClick = {
                    telephoneEntries.add(TelephoneEntry(mutableStateOf("")))
                }) {
                    Icon(
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = "add",
                        tint = DarkPink,
                    )
                }


            }
        } else {
            telephoneEntries.forEachIndexed { index, entry ->
                formAEDString(
                    text = "Telephone",
                    tfValue = entry.telNum.value,
                    showIconButton = index == telephoneEntries.size - 1,
                    onIconButtonClick = {
                        telephoneEntries.add(TelephoneEntry(mutableStateOf("")))
                    },
                    onRemoveIcon = {
                        telephoneEntries.removeAt(index)
                    },
                    onValueChange = { newValue ->
                        telephoneEntries[index].telNum.value = newValue
                    }
                )

            }
        }
    }
    return telephoneEntries.toMutableStateList()
}


@Composable
fun EditableTelForm(phoneNumber: String): SnapshotStateList<TelephoneEntry> {
    val telephoneEntries = remember {
        mutableStateListOf<TelephoneEntry>().apply {
            phoneNumber
                .split(";")
                .map { phone -> TelephoneEntry(mutableStateOf(phone.trim())) }.let {
                    addAll(it)
                }
        }

    }


    Column {
        FormTelephone(telephoneEntries)
    }
    return telephoneEntries

}

private fun readTooltipsInfo(context: Context): List<String> {
    val inputStream = context.resources.openRawResource(R.raw.tooltips)
    val reader = BufferedReader(InputStreamReader(inputStream))
    var line: String? = reader.readLine()
    val lines = mutableListOf<String>()
    while (line != null) {
        lines.add(line)
        line = reader.readLine()
    }


    reader.close()
    return lines
}

@Preview(showBackground = true)
@Composable
fun AddEditAedScreenPreview() {
    //AddEditAedScreen(rememberNavController())
}
