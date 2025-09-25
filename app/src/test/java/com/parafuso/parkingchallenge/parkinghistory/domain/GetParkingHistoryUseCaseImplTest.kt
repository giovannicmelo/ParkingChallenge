package com.parafuso.parkingchallenge.parkinghistory.domain

import app.cash.turbine.test
import com.parafuso.parkingchallenge.feature.parkinghistory.domain.model.ParkingHistory
import com.parafuso.parkingchallenge.feature.parkinghistory.domain.repository.ParkingHistoryRepository
import com.parafuso.parkingchallenge.feature.parkinghistory.domain.usecase.GetParkingHistoryUseCase
import com.parafuso.parkingchallenge.feature.parkinghistory.domain.usecase.GetParkingHistoryUseCaseImpl
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
class GetParkingHistoryUseCaseImplTest {

    private val repository: ParkingHistoryRepository = mockk(relaxed = true)
    private val doParkingUseCase: GetParkingHistoryUseCase =
        GetParkingHistoryUseCaseImpl(repository)

    @Test
    fun `use cases's invoke should return flow of list of parking history when plate is valid`() = runBlocking {
        // Given
        val params = GetParkingHistoryUseCase.Params("GCM-9998")
        val parkingHistory = listOf(
            ParkingHistory(
                left = true,
                paid = true,
                plate = "GCM-9998",
                reservation = "68d348",
                time = "13 hours 42 minutes"
            )
        )
        every {
            repository.getParkingHistory(params.plate)
        } returns flowOf(parkingHistory)

        // When
        val result = doParkingUseCase.invoke(params)

        // Then
        result.test {
            assertEquals(parkingHistory, expectItem())
            verify(exactly = 1) { repository.getParkingHistory(params.plate) }
            expectComplete()
        }
    }

    @Test
    fun `use cases's invoke should throw an exception when plate is invalid`() = runBlocking {
        // Given
        val params = GetParkingHistoryUseCase.Params("")
        val exception = Throwable("Error")
        every { repository.getParkingHistory(params.plate) } returns flow { throw exception }

        // When
        val result = doParkingUseCase.invoke(params)

        // Then
        result.test {
            assertEquals(exception, expectError())
            verify(exactly = 1) { repository.getParkingHistory(params.plate) }
            expectNoEvents()
        }
    }
}