package com.parafuso.parkingchallenge.feature.parkinghistory.domain.model

data class ParkingHistory(
    val left: Boolean,
    val paid: Boolean,
    val plate: String,
    val reservation: String,
    val time: String,
)