package com.aloel.dev.github.core.networks

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

/**
 * Created by Alul on 27/04/2025.
 * Senior Android Developer
 */


suspend fun <T> safeApiCall(apiCall: suspend () -> T): Resource<T> {
    return withContext(Dispatchers.IO) {
        try {
            val response = apiCall.invoke()
            Resource.Success(response)
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> Resource.Error("Network error. Please check your connection.")
                is HttpException -> {
                    val errorBody = throwable.response()?.errorBody()?.string() ?: ""
                    when (throwable.code()) {
                        401 -> Resource.Error("Unauthorized: $errorBody")
                        403 -> Resource.Error("Forbidden: $errorBody")
                        404 -> Resource.Error("Not Found: $errorBody")
                        500 -> Resource.Error("Internal Server Error: $errorBody")
                        else -> Resource.Error("HTTP ${throwable.code()}: $errorBody")
                    }
                }
                else -> Resource.Error(throwable.message ?: "Unknown error")
            }
        }
    }
}