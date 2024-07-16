package com.maxlabs.asdemo.util

sealed class Resource<T>(
    val code: Int? = null,
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(code: Int, data: T) : Resource<T>(code,data)
    class Loading<T>(code: Int, data: T? = null) : Resource<T>(code, data)
    class Error<T>(code: Int, message: String, data: T? = null) : Resource<T>(code, data, message)
}