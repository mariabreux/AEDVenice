package com.finalproject.aedvenice.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finalproject.aedvenice.data.ViewModel
import com.finalproject.aedvenice.ui.theme.BorderPink
import com.finalproject.aedvenice.ui.theme.DarkPink

@Composable
fun ManageAedScreen(viewModel: ViewModel){
    val aedBasics = viewModel.aeds.value

    //val aed = viewModel.getAedById("8Vc1mw4G6wtTFEu1S5FJ").observeAsState()

    var tableData by remember { mutableStateOf(emptyList<TableRow>()) }
    var newAddressText by remember { mutableStateOf("") }
    var newNotesText by remember { mutableStateOf("") }

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
                text = "AEDs",
                style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.SemiBold),
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                modifier = Modifier
                    .size(60.dp),
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkPink,
                ),
                border = BorderStroke(2.dp, BorderPink),
                onClick = {
                    tableData = tableData + TableRow(newAddressText, newNotesText)
                    newAddressText = ""
                    newNotesText = ""
                }
            ) {
                Text(
                    text = "+",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                )

            }
        }

        LazyColumn {
            itemsIndexed(aedBasics) { _, aed ->
                Row(
                ) {
                    aed.address?.let {
                        Text(
                            text = it,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .weight(2f)
                                .padding(5.dp)
                        )
                    }
                    aed.notes?.let {
                        Text(
                            text = it,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .weight(2f)
                                .padding(5.dp)
                        )
                    }
                    /*Text(
                        text = aed.geoPoint?.latitude.toString(),
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .weight(2f)
                            .padding(5.dp)
                    )
                    Text(
                        text = aed.geoPoint?.longitude.toString(),
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .weight(2f)
                            .padding(5.dp)
                    )*/

                }
            }
        }
        /*Button(onClick = { viewModel.deleteAed("HUvIKIc9kJK3wrr01m51") }) {
            Text(text = "delete")
        }*/
        Spacer(modifier = Modifier.padding(30.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = "Address", modifier = Modifier.weight(1f))
            Text(text = "Notes", modifier = Modifier.weight(1f))
            Text(text = "Actions", modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.padding(15.dp))

        // Table rows
        tableData.forEach { row ->
            TableRowItem(row = row)
            Spacer(modifier = Modifier.padding(bottom = 8.dp))
        }


        // New row input
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp)
        ) {
            OutlinedTextField(
                value = newAddressText,
                onValueChange = { newAddressText = it },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            )
            //Spacer(modifier = Modifier.width(8.dp))
            OutlinedTextField(
                value = newNotesText,
                onValueChange = { newNotesText = it },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()

            )
            Spacer(modifier = Modifier.width(6.dp))
            Spacer(modifier = Modifier.weight(1f))

        }

    }


}

@Composable
fun TableRowItem(row: TableRow) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = row.address, modifier = Modifier.weight(1f))
        Text(text = row.notes, modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.weight(1f))
    }
}


data class TableRow(val address: String, val notes: String)

/*
@Preview(showBackground = true)
@Composable
fun ManageAedScreenPreview() {
    ManageAedScreen(viewModel)
}
*/