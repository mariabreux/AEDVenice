package com.finalproject.aedvenice.maps

import android.location.Location

//data class MapState(
//    val properties : MapProperties = MapProperties(),
//    val isFalloutMap : Boolean = false
//)

data class MapState(
    val lastKnownLocation: Location?,
)
