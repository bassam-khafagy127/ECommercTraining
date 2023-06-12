package com.example.ecommerc_training.viewmodel.loginregister

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerc_training.utill.RegisterValidation
import com.example.ecommerc_training.utill.RegistrationFieldState
import com.example.ecommerc_training.utill.Resource
import com.example.ecommerc_training.utill.validateEmail
import com.example.ecommerc_training.utill.validatePassword
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val firebaseAut: FirebaseAuth
) : ViewModel() {
    private val _login = MutableStateFlow<Resource<FirebaseUser>>(Resource.Unspecified())
    val login = _login.asStateFlow()

    private val _registerValidation = Channel<RegistrationFieldState>()
    val validation = _registerValidation.receiveAsFlow()


    private val _resetPassword = MutableSharedFlow<Resource<String>>()
    val resetPassword = _resetPassword.asSharedFlow()

    fun signInWithEmailPassWord(email: String, password: String) {
        if (chickValidation(email, password)) {
            viewModelScope.launch {
                _login.emit(Resource.Loading())
            }
            firebaseAut.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {

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
        } else {
            val registerFieldState =
                RegistrationFieldState(validateEmail(email), validatePassword(password))

            viewModelScope.launch {
                _registerValidation.send(registerFieldState)

            }
        }
    }

    private fun chickValidation(email: String, password: String): Boolean {
        val emailValidate = validateEmail(email)
        val passwordValidate = validatePassword(password)

        return (emailValidate is RegisterValidation.Success
                && passwordValidate is RegisterValidation.Success)
    }

    fun resetPassword(email: String) {

        viewModelScope.launch {
            _resetPassword.emit(Resource.Loading())
        }

        firebaseAut.sendPasswordResetEmail(email)
            .addOnSuccessListener {
                viewModelScope.launch {
                    _resetPassword.emit(Resource.Success(email))
                }

            }.addOnFailureListener {
                viewModelScope.launch {
                    _resetPassword.emit(Resource.Error(it.message))
                }
            }
    }
}