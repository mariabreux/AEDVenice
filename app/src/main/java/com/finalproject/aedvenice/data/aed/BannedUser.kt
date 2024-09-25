package com.finalproject.aedvenice.data.aed

data class BannedUser (
    val uuid: String
)

data class User(
    val uuid: String,
    var reports: Int ?= 1
)