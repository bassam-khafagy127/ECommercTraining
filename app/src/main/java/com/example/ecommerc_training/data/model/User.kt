package com.example.ecommerc_training.data.model

data class User(
    val firstName: String,
    val lastName: String,
    val email: String
) {
    constructor() : this("","","")
}