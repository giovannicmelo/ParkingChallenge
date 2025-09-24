package com.parafuso.parkingchallenge.feature.parking.domain.model

data class Parking(
    val enteredAt: String,
    val plate: String,
    val reservation: String,
)