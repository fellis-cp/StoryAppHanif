package com.dicoding.storyapphanif.data

sealed class Result<out R> private constructor() {
    data class Error(val error: String) : Result<Nothing>()
    data class Success<out T>(val data: T) : Result<T>()
    object Loading : Result<Nothing>()
}