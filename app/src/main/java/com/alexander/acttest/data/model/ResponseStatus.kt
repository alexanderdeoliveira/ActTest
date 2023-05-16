package com.alexander.acttest.data.model

import retrofit2.Response
import java.net.HttpURLConnection

sealed class ResponseStatus<out Response> {
    data class Loading(val showLoading: Boolean): ResponseStatus<Nothing>()
    data class Success<Response> (val value : Response): ResponseStatus<Response>()
    data class Failure(val statusCode: Int, val message: String): ResponseStatus<Nothing>()
}

fun <R : Any> Response<R>.parseResponse(): ResponseStatus<R> {
    return if (isSuccessful) {
        val body = body()

        if (body != null) {
            ResponseStatus.Success(body)
        } else {
            ResponseStatus.Failure(HttpURLConnection.HTTP_INTERNAL_ERROR, "")
        }
    } else {
        ResponseStatus.Failure(code(), message())
    }
}