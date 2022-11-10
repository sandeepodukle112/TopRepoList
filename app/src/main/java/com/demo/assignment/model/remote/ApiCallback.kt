package com.demo.assignment.model.remote

import okhttp3.ResponseBody

sealed class ApiCallback<out T> {
    data class Success<out T>(val value: T) : ApiCallback<T>()
    data class Failure(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorResponse: ResponseBody?
    ) : ApiCallback<Nothing>()

}
