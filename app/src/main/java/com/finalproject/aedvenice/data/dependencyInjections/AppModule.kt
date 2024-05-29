package com.finalproject.aedvenice.data.dependencyInjections

import com.finalproject.aedvenice.data.auth.AuthRepository
import com.finalproject.aedvenice.data.auth.AuthRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) //our object will live as long as our application does
object AppModule {
    @Provides
    @Singleton
    fun providesFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun providesRepositoryImpl(firebaseAuth: FirebaseAuth) : AuthRepository{
        return AuthRepositoryImpl(firebaseAuth)
    }
}