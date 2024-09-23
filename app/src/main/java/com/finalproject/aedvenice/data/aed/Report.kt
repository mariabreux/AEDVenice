package com.finalproject.aedvenice.data.aed

data class Report(
    val id: String ?= null,
    var aedId : String ?= null,
    var coordinates : GeoPoint ?= null,
    var message : String ?= null,
    var uuid : String ?= null
)
