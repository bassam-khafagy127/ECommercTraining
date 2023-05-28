package com.example.ecommerc_training.utill

sealed class Resource<T>
    (
    val data: T? = null,
    val message: String? = ""
) {
    class Success<T>(data: T?) : Resource<T>(data)
    class Error<T>(message: String?) : Resource<T>(message = message)
    class Loading<T> : Resource<T>()
    class Unspecified<T> : Resource<T>()

}
