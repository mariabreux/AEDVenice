package com.finalproject.aedvenice.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.finalproject.aedvenice.data.aed.Aed
import com.finalproject.aedvenice.ui.theme.BorderPink
import com.finalproject.aedvenice.ui.theme.DarkPink
import com.finalproject.aedvenice.ui.theme.LightPink
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun parseTimetable(json: String): Map<String, String> {
    val gson = Gson()
    val mapType = object : TypeToken<Map<String, String>>() {}.type

    return gson.fromJson(json, mapType)
}


@Composable
fun AedDetailsScreen(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    navController: NavHostController,
    aed: Aed?,
    aedId: String?,
    showButton: Boolean
) {

    val scheduleMap = aed?.timetable?.trimIndent()?.let { parseTimetable(it) }

    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(5.dp))
                .height(560.dp)
                .width(300.dp)
                .background(LightPink)
                .border(2.dp, color = DarkPink, shape = RoundedCornerShape(5.dp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Top,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 10.dp)
            ) {
                Text(
                    text = "AED: " + aed?.name,
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold),
                )

            }
            Spacer(modifier = Modifier.padding(10.dp))

            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp)
            ) {
                Text(
                    text = "Address: " + aed?.aedBasics?.address,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
                )

                Spacer(modifier = Modifier.padding(10.dp))

                Text(
                    text = "Timetable:",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
                )
            }
            Spacer(modifier = Modifier.padding(10.dp))

            LazyColumn(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(10.dp))
                    .background(Color.White)
                    .height(125.dp)
                    .width(200.dp)

            ) {

                if (scheduleMap != null) {
                    items(scheduleMap.toList()) { (day, hours) ->
                        Row {
                            Text(
                                text = day,
                                fontSize = 10.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(10.dp)
                            )
                            Divider(
                                modifier = Modifier
                                    .height(52.dp)
                                    .width(1.dp),
                                color = Color.LightGray
                            )

                            Column(modifier = Modifier.weight(1f).padding(10.dp)) {
                                val hourList = hours.split("/").map { it.trim() }
                                hourList.forEach { hour ->
                                    Text(
                                        text = hour,
                                        fontSize = 10.sp,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = 4.dp)
                                    )

                                }
                            }
                        }
                        Divider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp),
                            color = Color.LightGray
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.padding(10.dp))


            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp)
            ) {
                if (aed?.aedBasics?.notes != null) {
                    Text(
                        text = "Notes: " + aed.aedBasics.notes,
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
                    )

                }
                Spacer(modifier = Modifier.padding(10.dp))

                if (aed?.phoneNumber != null) {
                    var numbers = aed.phoneNumber.split(";")
                    var cnt = 0
                    if(numbers.size > 1){
                        for(i in numbers){
                            cnt++
                            Text(
                                text = "Telephone$cnt: $i",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
                            )
                        }
                    }
                    else{
                        Text(
                            text = "Telephone: " + aed.phoneNumber,
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
                        )
                    }


                }

            }

            Spacer(modifier = Modifier.height(55.dp))

            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkPink,
                ),
                border = BorderStroke(2.dp, BorderPink),
                onClick = { onDismiss() }
            ) {
                Text(
                    text = "Exit",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                )
            }
            Spacer(modifier = Modifier.padding(3.dp))
            if (showButton) {
                Text(
                    text = "Report problem",
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                    color = DarkPink,
                    textDecoration = TextDecoration.Underline,

                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onDismiss()
                            navController.navigate("Report/$aedId")
                        },
                    textAlign = TextAlign.Center
                )
            }

        }

    }

}

