package com.parafuso.parkingchallenge.core.di

import com.parafuso.parkingchallenge.BuildConfig
import com.parafuso.parkingchallenge.core.data.remote.HttpClient
import com.parafuso.parkingchallenge.core.data.remote.LoggingInterceptorFactory
import com.parafuso.parkingchallenge.core.data.remote.OkHttpClientFactory
import com.parafuso.parkingchallenge.core.data.remote.RetrofitFactory
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.converter.gson.GsonConverterFactory

fun loadCoreModule(): List<Module> = listOf(
    module {
        single {
            HttpClient(
                retrofit = RetrofitFactory(
                    baseUrl = BuildConfig.BASE_URL,
                    okHttpClient = OkHttpClientFactory(
                        interceptors = listOf(
                            LoggingInterceptorFactory.create()
                        )
                    ).create(),
                    converterFactory = GsonConverterFactory.create()
                ).create()
            )
        }
    }
)