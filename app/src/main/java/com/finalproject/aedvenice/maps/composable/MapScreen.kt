package com.finalproject.aedvenice.maps.composable

import android.location.Location
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.AlertDialog
import androidx.compose.material.TextButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.finalproject.aedvenice.data.ViewModel
import com.finalproject.aedvenice.data.aed.Aed
import com.finalproject.aedvenice.maps.MapState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

@Composable
fun MapScreen(
    state: MapState,
    viewModel: ViewModel,
    navController : NavController
) {
    val aedBasics = viewModel.aeds.value
    val showDialog = remember { mutableStateOf(false) }
    var aedId by remember { mutableStateOf<String?>("") }
    val aedState by viewModel.getAedById(aedId ?: "").observeAsState(initial = null)

    // Set properties using MapProperties to recompose the map
    val mapProperties = MapProperties(
        // Only enable if user has accepted location permissions.
        isMyLocationEnabled = state.lastKnownLocation != null,
    )
    var loc = Location("dummyprovider")
    if(state.lastKnownLocation == null){
        loc.latitude = 0.0
        loc.longitude = 0.0
    }
    else
        loc = state.lastKnownLocation
    val cameraPositionState = rememberCameraPositionState()
    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        LaunchedEffect(key1 = loc) {
            cameraPositionState.centerOnLocation(loc)
        }
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            properties = mapProperties,
            cameraPositionState = cameraPositionState
        ) {

            for(i in aedBasics){
                if(i.geoPoint?.latitude != null && i.geoPoint.longitude != null){
                    MarkerInfoWindow(
                        state = rememberMarkerState(position = LatLng(i.geoPoint.latitude, i.geoPoint.longitude)),
                        onClick = {
                            i.id?.let {it1 ->
                                aedId = it1
                                showDialog.value = true
                            }
                            false
                        },
                        draggable = true
                    )
                }

            }
        }
    }

    if(showDialog.value){
        if(aedId != null){
            MyDialog(
                aedState,
                aedId,
                viewModel,
                onDismiss = { },
                navController = navController,
                showDialog = showDialog
            )
        }
    }
}

@Composable
fun MyDialog(
    aed: Aed?,
    aedId: String?,
    viewModel: ViewModel,
    navController : NavController,
    showDialog: MutableState<Boolean>,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {
            showDialog.value = false
            onDismiss()
        },
        text = {
            Text(
                "AED: " + aed?.name//.value?.name
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    /*TODO*/
                    if(aedId != null)
                        navController.navigate("Report/$aedId")
                    showDialog.value = false
                    onDismiss()
                }
            ) {
                /*TODO*/
                Text("Report Problem:")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    showDialog.value = false
                    onDismiss()
                }
            ) {
                /*TODO: button go to location*/
                Text("Exit")
            }
        }
    )
}

private suspend fun CameraPositionState.centerOnLocation(
    location: Location
) = animate(
    update = CameraUpdateFactory.newLatLngZoom(
        LatLng(location.latitude, location.longitude),
        15f
    ),
)