package com.finalproject.aedvenice.data.aed

data class AedBasics(
    val id : String ?= null,
    val address : String ?= null,
    val geoPoint : GeoPoint ?= null,
    val notes : String ?= null
)

data class Aed(
    val aedBasics: AedBasics ?= null,
    val name : String ?= null,
    val city : String ?= null,
    val location : String ?= null,
    val timetable : String ?= null,
    //val phoneNumber : List<String> ?= emptyList()
    val phoneNumber : String ?= null
)

data class GeoPoint(
    val latitude : Double ?= 0.0,
    val longitude : Double ?= 0.0
)