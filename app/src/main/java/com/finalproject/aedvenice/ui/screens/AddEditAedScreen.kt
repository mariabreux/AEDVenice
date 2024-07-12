package com.finalproject.aedvenice.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
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
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.finalproject.aedvenice.R

@Composable
fun AddEditAedScreen(navController: NavHostController) {

    var name by remember {
        mutableStateOf("")
    }

    var showDays by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()

    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp)

        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    //.fillMaxWidth()
                    .padding(top = 40.dp)
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
                modifier = Modifier
                    .padding(top = 40.dp)
            ) {
                IconButton(
                    onClick = { /*TODO: save info*/ },
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

        Spacer(modifier = Modifier.padding(15.dp))


        formAED(text = "Name", tfValue = name)
        formAED(text = "Address", tfValue = name)
        formAED(text = "Coordinates", tfValue = name)
        formAED(text = "City", tfValue = name)
        formAED(text = "Location", tfValue = name)
        formAED(text = "Telephone", tfValue = name)
        formAED(text = "Note", tfValue = name)

        Text(text = "Timetable: ")
        Spacer(modifier = Modifier.padding(10.dp))
        Row{
            DropdownMenu(expanded = showDays, onDismissRequest = {showDays = false}) {

            }
        }

        //TODO: telephone
        //TODO: timetable

    }
}


@SuppressLint("Range")
@Composable
fun formAED(text: String, tfValue: String, xValue: String = " ", yValue: String = " ") {

    when (text) {
        "Coordinates" -> {
            Row {
                Column(
                    modifier = Modifier.padding(10.dp)
                ) {
                    Row {
                        Text(
                            text = text + ":",
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
                            //TODO: show info when pass with the mouse
                        )
                    }
                }

                Column(
                    modifier = Modifier.padding(10.dp)
                ) {
                    var x by remember {
                        mutableStateOf(xValue)
                    }
                    var y by remember {
                        mutableStateOf(yValue)
                    }
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
        else -> {

            Row {
                Column(
                    modifier = Modifier.padding(10.dp)
                ) {
                    Row {
                        Text(
                            text = text + ":",
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
                            //TODO: show info when pass with the mouse
                        )
                    }

                }

                Column(
                    modifier = Modifier.padding(10.dp)
                ) {
                    var tf by remember {
                        mutableStateOf(tfValue)
                    }

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


@Preview(showBackground = true)
@Composable
fun AddEditAedScreenPreview() {
    AddEditAedScreen(rememberNavController())
}
