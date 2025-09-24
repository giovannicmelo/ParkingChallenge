package com.parafuso.parkingchallenge.core.data.util

import com.google.gson.Gson
import com.parafuso.parkingchallenge.core.data.model.ApiResponseError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import retrofit2.HttpException

fun <T> Flow<T>.parseHttpError(): Flow<T> {
    return catch { throwable ->
        throwable.getErrorBody()?.let { errorBody ->
            Gson().fromJson(errorBody, ApiResponseError::class.java)
        } ?: throw throwable
    }
}

internal fun Throwable.getErrorBody(): String? =
    (this as? HttpException)?.response()?.errorBody()?.string()
