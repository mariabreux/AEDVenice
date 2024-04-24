package com.finalproject.aedvenice.maps

import com.google.maps.android.compose.MapProperties

data class MapState(
    val properties : MapProperties = MapProperties(),
    val isFalloutMap : Boolean = false
)
