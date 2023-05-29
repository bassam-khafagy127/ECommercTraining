package com.example.ecommerc_training.utill

sealed class RegisterValidation {
    object Success : RegisterValidation()
    data class Failed(val message: String) : RegisterValidation()
}

data class RegistrationFieldState(
    val email: RegisterValidation,
    val password: RegisterValidation
)