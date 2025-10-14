package com.parafuso.parkingchallenge.feature.parking.presentation.state

import com.parafuso.parkingchallenge.feature.parking.domain.model.Parking

data class ParkingViewState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val isValidPlate: Boolean = false,
    val plate: String = "",
    val parking: Parking? = null
)