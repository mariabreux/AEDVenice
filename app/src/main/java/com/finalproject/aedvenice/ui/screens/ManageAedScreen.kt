package com.finalproject.aedvenice.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.finalproject.aedvenice.R
import com.finalproject.aedvenice.data.ViewModel
import com.finalproject.aedvenice.data.aed.Aed
import com.finalproject.aedvenice.data.aed.AedBasics
import com.finalproject.aedvenice.ui.theme.BorderPink
import com.finalproject.aedvenice.ui.theme.DarkPink
import com.finalproject.aedvenice.ui.theme.LightPink

@Composable
fun ManageAedScreen(viewModel: ViewModel, navController: NavHostController) {
    var showDialog by remember { mutableStateOf(false) }
    var deleteDialog by remember { mutableStateOf(false) }
    var onDismiss = { showDialog = false }
    var aedId by remember { mutableStateOf<String?>("") }
    val aedState by viewModel.getAedById(aedId ?: "").observeAsState(initial = null)
    var isLoading by remember { mutableStateOf(true) }
    var aedBasics by remember {
        mutableStateOf(emptyList<AedBasics>())
    }

    LaunchedEffect(Unit) {
        //aedBasics = viewModel.aeds.value
        //isLoading = false
        viewModel.getAedBasicsList { aeds ->
            aedBasics = aeds
            isLoading = false
        }
    }


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
                    navController.navigate("AddEditAed")
                }
            ) {
                Text(
                    text = "+",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
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
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "Notes", modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "Actions", modifier = Modifier.weight(1f))
            }

            if (isLoading) {
                ShimmerEffect()
            } else {
                Spacer(modifier = Modifier.padding(15.dp))
            }

            //Spacer(modifier = Modifier.padding(15.dp))

            LazyColumn(
                modifier = Modifier
                    .border(BorderStroke(1.dp, Color.LightGray), shape = RoundedCornerShape(5.dp))
            ) {
                item {


                    aedBasics.forEach { aed ->
                        Row {
                            Text(
                                text = aed.address ?: "", modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = 15.dp)
                                    .align(Alignment.CenterVertically)
                            )

                            Divider(
                                modifier = Modifier
                                    .height(360.dp)
                                    .width(1.dp),
                                color = Color.LightGray
                            )


                            Text(
                                text = aed.notes ?: "", modifier = Modifier
                                    .weight(1.5f)
                                    .padding(horizontal = 3.dp)
                                    .align(Alignment.CenterVertically)

                            )

                            Divider(
                                modifier = Modifier
                                    .height(360.dp)
                                    .width(1.dp),
                                color = Color.LightGray
                            )

                            Column(modifier = Modifier.offset(0.dp, (140).dp)) {
                                IconButton(onClick = {
                                    navController.navigate(
                                        "AddEditAed?aedId=${aed.id}"
                                    )
                                }) {
                                    Icon(
                                        imageVector = ImageVector.vectorResource(id = R.drawable.edit),
                                        tint = Color.Unspecified,
                                        contentDescription = "edit",
                                        modifier = Modifier
                                            .size(25.dp)
                                    )
                                }

                                IconButton(onClick = {
                                    aedId = aed.id
                                    deleteDialog = true
//                                    aed.id?.let {
//                                        viewModel.deleteAed(it, {
//                                            Log.d("Delete Aed", "Aed deleted")
//                                        },
//                                            {
//                                                Log.e("Delete Aed", "Error deleting Aed")
//
//                                            })
//                                    }
                                }) {
                                    Icon(
                                        imageVector = ImageVector.vectorResource(id = R.drawable.delete),
                                        tint = Color.Unspecified,
                                        contentDescription = "delete",
                                        modifier = Modifier
                                            .size(25.dp)
                                    )
                                }
                                Text(
                                    text = "More info",
                                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                                    color = DarkPink,
                                    textDecoration = TextDecoration.Underline,

                                    modifier = Modifier
                                        .padding(horizontal = 5.dp)
                                        //.align(Alignment.CenterVertically)
                                        .clickable {
                                            aedId = aed.id
                                            showDialog = true
                                        },
                                    textAlign = TextAlign.Center
                                )

                            }
                        }
                        Divider()
                    }
                }

            }


        }


    }
    if (showDialog) {
        AedDetailsScreen(
            onDismiss = { onDismiss() },
            onConfirm = { },
            navController = navController,
            aed = aedState,
            aedId = aedState?.aedBasics?.id,
            showButton = false
        )
    }

    if(deleteDialog){
        DeleteConfirmation(onDismiss = { deleteDialog = false }, aed = aedState, aedId, viewModel = viewModel)

    }


}

@Composable
fun DeleteConfirmation(
    onDismiss: () -> Unit,
    aed: Aed?,
    aedId: String?,
    viewModel: ViewModel,
){
    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(5.dp))
                .height(300.dp)
                .width(310.dp)
                .background(LightPink)
                .border(2.dp, color = DarkPink, shape = RoundedCornerShape(5.dp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Top,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .padding(horizontal = 5.dp)
            ) {
                androidx.compose.material.Text(
                    text = aed?.name.toString(),
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold),
                )
            }
            Spacer(modifier = Modifier.padding(20.dp))

            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp)
            ) {
                androidx.compose.material.Text(
                    text = "Are you sure you want to delete this AED?",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
                )

            }
            Spacer(modifier = Modifier.padding(25.dp))

            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkPink,
                ),
                border = BorderStroke(2.dp, BorderPink),
                onClick = {
                    aedId?.let {
                        viewModel.deleteAed(it, {
                            Log.d("Delete Aed", "Aed deleted")
                        },
                            {
                                Log.e("Delete Aed", "Error deleting Aed")

                            })
                    }
                    onDismiss()
                }
            ) {
                androidx.compose.material.Text(
                    text = "Delete AED",
                    color = Color.White,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                )
            }

            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkPink,
                ),
                border = BorderStroke(2.dp, BorderPink),
                onClick = {
                    onDismiss()
                }
            ) {
                androidx.compose.material.Text(
                    text = "Cancel",
                    color = Color.White,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}