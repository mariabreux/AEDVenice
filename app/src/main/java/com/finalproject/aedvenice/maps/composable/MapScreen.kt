package com.finalproject.aedvenice.maps.composable

import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.finalproject.aedvenice.R
import com.finalproject.aedvenice.data.ViewModel
import com.finalproject.aedvenice.data.aed.AedBasics
import com.finalproject.aedvenice.data.aed.BannedUser
import com.finalproject.aedvenice.maps.MapState
import com.finalproject.aedvenice.ui.screens.AedDetailsScreen
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import java.io.IOException
import java.util.Locale

@Composable
fun MapScreen(
    state: MapState,
    viewModel: ViewModel,
    navController : NavHostController
) {
    //val aedBasics = viewModel.aeds.value
    val showDialog = remember { mutableStateOf(false) }
    var aedId by remember { mutableStateOf<String?>("") }
    val aedState by viewModel.getAedById(aedId ?: "").observeAsState(initial = null)
    var bannedUsers by remember { mutableStateOf(emptyList<BannedUser>()) }
    var isUserBanned by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }
    var aedBasics by remember { mutableStateOf(emptyList<AedBasics>()) }
    var searchValue by remember { mutableStateOf("") }
    var searchLocation by remember { mutableStateOf<Location?>(null) }

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.getAedBasicsList { aeds ->
            aedBasics = aeds
            //Toast.makeText(context, "All aeds are loaded" + aedBasics.size, Toast.LENGTH_SHORT).show()
        }
        viewModel.getBannedUsers { bU ->
            bannedUsers = bU
            val currentUser = BannedUser(viewModel.getUUID(context))
            if (bannedUsers.contains(currentUser)) {
                isUserBanned = true
            }
            isLoading = false
        }
    }

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

    //Map
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
                Log.i("AED", i.id.toString() + " " + i.geoPoint.toString())
                if(i.geoPoint?.latitude != null && i.geoPoint.longitude != null){
                    MarkerInfoWindow(
                        state = rememberMarkerState(position = LatLng(i.geoPoint.latitude, i.geoPoint.longitude)),
                        onClick = {
                            if(isLoading){
                                Toast.makeText(context, "Loading. Please wait...", Toast.LENGTH_SHORT).show()
                            }
                            else{
                                i.id?.let {it1 ->
                                    aedId = it1
                                    showDialog.value = true
                                }
                            }
                            false
                        },
                        draggable = true
                    )
                }
            }
        }
    }

    //Search
    LaunchedEffect(searchLocation) {
        searchLocation?.let { location ->
            cameraPositionState.centerOnLocation(location)
        }
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ){
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
                        text = "Search by city name",
                        modifier = Modifier.offset((-30).dp)
                    )
                }
            },

            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(backgroundColor = Color.White),
            modifier = Modifier
                .height(75.dp)
                .padding(10.dp),

            leadingIcon = {
                IconButton(onClick = {
                    searchLocation = getLocation(context, searchValue)
                    if(searchLocation == null){
                        Toast.makeText(context, "Error finding location", Toast.LENGTH_SHORT).show()
                    }
                }) {
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

    if(showDialog.value){
        if(aedId != null){
            AedDetailsScreen(onDismiss = { showDialog.value = false }, onConfirm = { }, navController, aedState, aedId, showButton = !isUserBanned)
        }
    }
}

fun getLocation(context: Context, location: String): Location? {
    val geocoder = Geocoder(context, Locale.getDefault())
    try {
        val adds = geocoder.getFromLocationName(location, 1)
        var loc : Location? = null
        if(!adds.isNullOrEmpty()){
            val add = adds[0]
            loc = Location("geocoder").apply {
                latitude = add.latitude
                longitude = add.longitude
            }
        }
        return loc
    }
    catch (e: IOException){
        e.printStackTrace()
        return null
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