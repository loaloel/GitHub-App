package com.aloel.dev.github.core.networks

/**
 * Created by Alul on 26/04/2025.
 * Senior Android Developer
 */

sealed class Resource<T> {
    class Loading<T> : Resource<T>()
    data class Success<T>(val data: T) : Resource<T>()
    data class Error<T>(val message: String) : Resource<T>()
}