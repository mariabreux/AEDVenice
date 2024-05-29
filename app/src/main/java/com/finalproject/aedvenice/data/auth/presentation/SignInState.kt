package com.finalproject.aedvenice.data.auth.presentation

data class SignInState (
    val isLoading : Boolean = false,
    val isSuccess : String ?= "",
    val isError : String ?= ""
)