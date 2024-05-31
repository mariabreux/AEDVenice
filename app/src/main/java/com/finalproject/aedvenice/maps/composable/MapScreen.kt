package com.finalproject.aedvenice.maps.composable

import android.location.Location
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.finalproject.aedvenice.data.ViewModel
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
    viewModel: ViewModel
) {
    val aedBasics = viewModel.aeds.value
    // Set properties using MapProperties which you can use to recompose the map
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
                            i.id?.let { it1 -> Log.i("Marker clicked!", it1) }
                            false
                        },
                        draggable = true
                    )
                }

            }
        }
    }
}

private suspend fun CameraPositionState.centerOnLocation(
    location: Location
) = animate(
    update = CameraUpdateFactory.newLatLngZoom(
        LatLng(location.latitude, location.longitude),
        15f
    ),
)