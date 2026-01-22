package com.parafuso.parkingchallenge.feature.parkinghistory.presentation.state

import com.parafuso.parkingchallenge.feature.parkinghistory.domain.model.ParkingHistory

data class ParkingHistoryViewState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val plate: String = "",
    val parkingHistory: List<ParkingHistory> = listOf(),
)