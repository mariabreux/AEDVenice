package com.finalproject.aedvenice.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.finalproject.aedvenice.maps.MapsViewModel
import com.finalproject.aedvenice.maps.composable.MapScreen
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings

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
fun HomeScreen(navController: NavHostController, viewModel: MapsViewModel){
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        MapScreen(state = viewModel.state.value)

    }

}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    //HomeScreen(navController = rememberNavController())
}