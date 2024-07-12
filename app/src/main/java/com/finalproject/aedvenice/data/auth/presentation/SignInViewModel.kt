package com.finalproject.aedvenice.data.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finalproject.aedvenice.data.auth.AuthRepository
import com.finalproject.aedvenice.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {
    private val _signInState = Channel<SignInState>()
    val signInState = _signInState.receiveAsFlow()

    fun loginUser(email : String, password : String) = viewModelScope.launch {
        repository.loginUser(email, password).collect{ result ->
         when(result){
             is Resource.Success ->{
                _signInState.send(SignInState(isSuccess = "Sign In Success"))
             }
             is Resource.Loading ->{
                _signInState.send(SignInState(isLoading = true))
             }
             is Resource.Error ->{
                 _signInState.send(SignInState(isError = result.message))
             }
         }
        }
    }

    fun logoutUser(){
        repository.logoutUser()
    }

    fun isUserLoggedIn() : Boolean{
        return repository.isUserLoggedIn()
    }

    fun removeUser(){
        repository.removeUser()
    }

    fun updatePassword(password: String){
        repository.updatePassword(password)
    }
}