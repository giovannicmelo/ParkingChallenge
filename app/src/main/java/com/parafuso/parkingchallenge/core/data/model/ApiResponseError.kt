package com.parafuso.parkingchallenge.core.data.model


import com.google.gson.annotations.SerializedName

data class ApiResponseError(
    @SerializedName("errors")
    val errors: Errors?
)