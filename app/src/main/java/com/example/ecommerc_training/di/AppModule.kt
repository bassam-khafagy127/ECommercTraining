package com.example.ecommerc_training.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.ecommerc_training.utill.NAVIGATION.INTRODUCTION_SHARED_PREFERENCE
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideFireBaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFireStoreDataBase() = Firebase.firestore

    @Provides
    fun provideIntroductionViewModel(application: Application) =
        application.getSharedPreferences(INTRODUCTION_SHARED_PREFERENCE, MODE_PRIVATE)

}