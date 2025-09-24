package com.parafuso.parkingchallenge.core.data.remote

import androidx.annotation.VisibleForTesting
import com.parafuso.parkingchallenge.core.data.model.ApiException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Modifier.PRIVATE

@VisibleForTesting(otherwise = PRIVATE)
fun createRetrofit(
    baseUrl: String,
    okHttpClient: OkHttpClient? = null,
    converterFactory: Converter.Factory = GsonConverterFactory.create(),
): Retrofit {
    val builder = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(converterFactory)
    okHttpClient?.let { builder.client(it) }

    return builder.build()
}

fun emptyBodyResponse(block: suspend () -> Response<Unit>): Flow<Unit> = flow {
    val result = block()
    if (result.isSuccessful) {
        emit(Unit)
    } else {
        throw ApiException(result.code(), result.message())
    }
}