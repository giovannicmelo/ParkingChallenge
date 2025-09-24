package com.parafuso.parkingchallenge.core.data.model


import com.google.gson.annotations.SerializedName

data class Errors(
    @SerializedName("plate")
    val plate: List<String?>?
)