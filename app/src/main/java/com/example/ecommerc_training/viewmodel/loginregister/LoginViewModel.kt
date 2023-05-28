package com.example.ecommerc_training.viewmodel.loginregister

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerc_training.data.model.User
import com.example.ecommerc_training.utill.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val firebaseAut: FirebaseAuth
) : ViewModel() {
    private val _login = MutableStateFlow<Resource<FirebaseUser>>(Resource.Unspecified())
    val login = _login.asStateFlow()
    fun signInWithEmailPassWord(user: User, password: String) {
        viewModelScope.launch {
            _login.emit(Resource.Loading())
        }

        firebaseAut.signInWithEmailAndPassword(user.email, password).addOnSuccessListener {
            viewModelScope.launch {
                it.user?.let {
                    _login.value = Resource.Success(it)
                }
            }
        }.addOnFailureListener {
            viewModelScope.launch {
                it.message?.let {
                    _login.value = Resource.Error(it)
                }
            }
        }

    }
}