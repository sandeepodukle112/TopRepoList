package com.demo.assignment.model.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

interface SafeApiCall {
    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): ApiCallback<T> {
        return withContext(Dispatchers.IO) {
            try {
                ApiCallback.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        ApiCallback.Failure(false, throwable.code(), throwable.response()?.errorBody())
                    }
                    else -> {
                        ApiCallback.Failure(true, null, null)
                    }
                }
            }
        }
    }
}