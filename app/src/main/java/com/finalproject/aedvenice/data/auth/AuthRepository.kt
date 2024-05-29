package com.finalproject.aedvenice.data.auth

import com.finalproject.aedvenice.utils.Resource
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun loginUser(email : String, password : String) : Flow<Resource<AuthResult>>

    fun registerUser(email : String, password : String) : Flow<Resource<AuthResult>>

}