package com.parafuso.parkingchallenge.core.data.remote

import androidx.annotation.VisibleForTesting
import okhttp3.OkHttpClient
import retrofit2.Converter
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