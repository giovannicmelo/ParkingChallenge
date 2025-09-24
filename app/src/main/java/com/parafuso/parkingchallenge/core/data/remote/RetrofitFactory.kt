package com.parafuso.parkingchallenge.core.data.remote

import com.parafuso.parkingchallenge.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

private const val TIMEOUT_SECONDS = 15L

class RetrofitFactory(
    private val baseUrl: String,
    private val okHttpClient: OkHttpClient,
    private val converterFactory: Converter.Factory,
) {

    fun create(): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(converterFactory)
        .build()
}

class OkHttpClientFactory(private val interceptors: List<Interceptor>) {

    fun create(): OkHttpClient = OkHttpClient.Builder()
        .apply { interceptors.forEach { addInterceptor(it) } }
        .apply {
            readTimeout(timeout = TIMEOUT_SECONDS, unit = TimeUnit.SECONDS)
            writeTimeout(timeout = TIMEOUT_SECONDS, unit = TimeUnit.SECONDS)
            connectTimeout(timeout = TIMEOUT_SECONDS, unit = TimeUnit.SECONDS)
        }
        .build()
}

object LoggingInterceptorFactory {

    fun create() = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }
}