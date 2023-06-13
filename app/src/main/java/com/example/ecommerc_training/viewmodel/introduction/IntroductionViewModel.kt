package com.example.ecommerc_training.viewmodel.introduction

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerc_training.utill.NAVIGATION.ACCOUNT_OPTION_FRAGMENT
import com.example.ecommerc_training.utill.NAVIGATION.IS_BUTTON_CLICKED
import com.example.ecommerc_training.utill.NAVIGATION.SHOPPING_ACTIVITY
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IntroductionViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    firebaseAuth: FirebaseAuth
) : ViewModel() {
    private val _navigation = MutableStateFlow(0)
    val navigation: StateFlow<Int> = _navigation


    init {
        val isButtonClicked = sharedPreferences.getBoolean(IS_BUTTON_CLICKED, false)
        val user = firebaseAuth.currentUser
        if (user != null) {
            viewModelScope.launch {
                _navigation.emit(SHOPPING_ACTIVITY)
            }
        } else if (isButtonClicked) {
            viewModelScope.launch {
                _navigation.emit(ACCOUNT_OPTION_FRAGMENT)
            }
        } else {
            Unit
        }
    }

    fun isButtonClickedFunction() {
        sharedPreferences.edit().putBoolean(IS_BUTTON_CLICKED, true).apply()
    }
}