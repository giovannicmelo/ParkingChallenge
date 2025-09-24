package com.parafuso.parkingchallenge.core.data.model

class ApiException(
    val statusCode: Int,
    override val message: String = "",
) : Throwable(message)