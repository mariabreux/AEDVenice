package com.finalproject.aedvenice.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.finalproject.aedvenice.R
import com.finalproject.aedvenice.data.ViewModel
import com.finalproject.aedvenice.maps.composable.MapScreen

//@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
//@Composable
//fun HomeScreen(
//    navController: NavHostController,
//    viewModel: MapsViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
//){
//    val scaffoldState = rememberScaffoldState()
//    val uiSettings = remember {
//        MapUiSettings(zoomControlsEnabled = false)
//    }
//
//    Scaffold(
//        scaffoldState = scaffoldState,
//    ) {
//        GoogleMap(
//            modifier = Modifier
//                .padding(16.dp)
//                .fillMaxSize(),
//            properties = viewModel.state.properties,
//            uiSettings = uiSettings
//        )
//    }
//}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavHostController, viewModel: ViewModel) {
    val scaffoldState = rememberScaffoldState()
    var searchValue by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    val onDismiss = { showDialog = false }
    val onConfirm = { }

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        MapScreen(state = viewModel.state.value)

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(20.dp)
                .padding(top = 25.dp)
        ) {
            OutlinedTextField(
                value = searchValue,
                onValueChange = { searchValue = it },

                placeholder = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 55.dp)
                    ) {
                        Text(
                            text = "Search",
                        )
                    }


                },

                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(backgroundColor = Color.White),
                modifier = Modifier
                    .height(70.dp)
                    .padding(10.dp),

                leadingIcon = {
                    IconButton(onClick = { showDialog = true }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.search),
                            tint = Color.Unspecified,
                            contentDescription = "search",
                            modifier = Modifier
                                .size(30.dp)
                        )
                    }

                }

            )
        }


    }
    if (showDialog) {
        AedDetailsScreen(onDismiss = onDismiss, onConfirm = onConfirm, navController)
    }

    //TODO: search and select an aed to show details

}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    //HomeScreen(navController = rememberNavController())
}