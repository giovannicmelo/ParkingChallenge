package com.parafuso.parkingchallenge.core.data.util

import com.google.gson.Gson
import com.parafuso.parkingchallenge.core.data.model.ApiException
import com.parafuso.parkingchallenge.core.data.model.ApiResponseError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import retrofit2.HttpException

fun <T> Flow<T>.parseHttpError(): Flow<T> {
    return catch { throwable ->
        when (throwable) {
            is HttpException -> {
                throwable.getErrorBody()?.let { errorBody ->
                    val errorResponse = Gson().fromJson(errorBody, ApiResponseError::class.java)
                    val errorMessage = errorResponse.errors?.plate?.firstOrNull().orEmpty()
                    throw ApiException(throwable.code(), errorMessage)
                }
            }
            else -> throw throwable
        }
    }
}

internal fun Throwable.getErrorBody(): String? =
    (this as? HttpException)?.response()?.errorBody()?.string()
