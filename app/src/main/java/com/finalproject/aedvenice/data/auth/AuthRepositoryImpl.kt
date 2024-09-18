package com.finalproject.aedvenice.data.auth

import com.finalproject.aedvenice.utils.Resource
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {
    override fun loginUser(email: String, password: String): Flow<Resource<AuthResult>> {
        return flow {
            emit(Resource.Loading())
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            emit(Resource.Success(result))
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }

    override fun registerUser(email: String, password: String): Flow<Resource<AuthResult>> {
        return flow {
            emit(Resource.Loading())
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            emit(Resource.Success(result))
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }

    override fun logoutUser() {
        if(isUserLoggedIn()){
            firebaseAuth.signOut()
        }
    }

    override fun isUserLoggedIn(): Boolean {
        return (FirebaseAuth.getInstance().currentUser != null)
    }

    override fun removeUser() {
        if(isUserRemovable())
            firebaseAuth.currentUser?.delete()
    }

    override fun updatePassword(password: String, currentPwd: String): Boolean {
        var result = true
        //Reauthenticate user
        val user = FirebaseAuth.getInstance().currentUser
        val email = user?.email ?: return false
        val credentials = EmailAuthProvider.getCredential(email, currentPwd)
        user.reauthenticate(credentials)
            .addOnSuccessListener {
                firebaseAuth.currentUser!!.updatePassword(password)
                    .addOnCompleteListener { task ->
                        result = task.isSuccessful
                    }
            }
        return result
    }

    override fun isUserRemovable(): Boolean {
        val user = FirebaseAuth.getInstance().currentUser
        return user?.email != "admin@admin.com" //TODO: if email = admin do toast: unable to remove user
    }
}