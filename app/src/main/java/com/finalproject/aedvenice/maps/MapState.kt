package com.finalproject.aedvenice.maps

import android.location.Location
import com.google.maps.android.compose.MapProperties

//data class MapState(
//    val properties : MapProperties = MapProperties(),
//    val isFalloutMap : Boolean = false
//)

data class MapState(
    val lastKnownLocation: Location?,
)
