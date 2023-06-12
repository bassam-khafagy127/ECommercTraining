package com.example.ecommerc_training.viewmodel.loginregister

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerc_training.data.model.User
import com.example.ecommerc_training.utill.Constant
import com.example.ecommerc_training.utill.Constant.DataBase.USERS_COLLECTION
import com.example.ecommerc_training.utill.RegisterValidation
import com.example.ecommerc_training.utill.RegistrationFieldState
import com.example.ecommerc_training.utill.Resource
import com.example.ecommerc_training.utill.validateEmail
import com.example.ecommerc_training.utill.validatePassword
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistryViewModel @Inject constructor(
    private val firebaseAut: FirebaseAuth,
    private val fireStoreDB: FirebaseFirestore
) : ViewModel() {
    private val _register = MutableStateFlow<Resource<User>>(Resource.Unspecified())
    val register = _register.asStateFlow()

    private val _registerValidation = Channel<RegistrationFieldState>()
    val validation = _registerValidation.receiveAsFlow()

    fun createUserWithEmailPassWord(user: User, password: String) {
        if (chickValidation(user, password)) {
            viewModelScope.launch {
                _register.emit(Resource.Loading())
            }
            firebaseAut.createUserWithEmailAndPassword(user.email, password)
                .addOnSuccessListener {
                    viewModelScope.launch {
                        it.user?.let {
                            saveUserInfo(user, it.uid)
                        }
                    }
                }.addOnFailureListener {
                    viewModelScope.launch {
                        it.message?.let {
                            _register.value = Resource.Error(it)
                        }
                    }
                }
        } else {
            val registerFieldState =
                RegistrationFieldState(validateEmail(user.email), validatePassword(password))
            viewModelScope.launch {
                _registerValidation.send(registerFieldState)

            }
        }
    }

    private fun saveUserInfo(user: User, userUid: String) {
        fireStoreDB.collection(USERS_COLLECTION)
            .document(userUid)
            .set(user)
            .addOnSuccessListener {
                _register.value = Resource.Success(user)
            }
            .addOnFailureListener {
                _register.value = Resource.Error(it.message.toString())
            }
    }

    private fun chickValidation(user: User, password: String): Boolean {
        val emailValidate = validateEmail(user.email)
        val passwordValidate = validatePassword(password)
        return (emailValidate is RegisterValidation.Success
                && passwordValidate is RegisterValidation.Success)
    }
}