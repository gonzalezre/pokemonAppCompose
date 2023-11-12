package com.example.pokemonapp.core.network

sealed class NetworkResult<out T> {
    data class Success<out T>(val data: T) : NetworkResult<T>()
    data class Error(val code: Int?, val message: String?) : NetworkResult<Nothing>()
    object NetworkError : NetworkResult<Nothing>()
}
