package com.finalproject.aedvenice.data.aed

data class Report(
    var aedId : String ?= null,
    var coordinates : GeoPoint ?= null,
    var message : String ?= null
)
