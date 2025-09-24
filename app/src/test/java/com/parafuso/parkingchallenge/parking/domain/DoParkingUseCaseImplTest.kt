package com.parafuso.parkingchallenge.parking.domain

import app.cash.turbine.test
import com.parafuso.parkingchallenge.feature.parking.domain.model.Parking
import com.parafuso.parkingchallenge.feature.parking.domain.repository.ParkingRepository
import com.parafuso.parkingchallenge.feature.parking.domain.usecase.DoParkingUseCase
import com.parafuso.parkingchallenge.feature.parking.domain.usecase.DoParkingUseCaseImpl
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
class DoParkingUseCaseImplTest {

    private val repository: ParkingRepository = mockk(relaxed = true)
    private val doParkingUseCase: DoParkingUseCase = DoParkingUseCaseImpl(repository)

    @Test
    fun `use cases's invoke should return flow of parking when repository return parking`() = runBlocking {
        // Given
        val params = DoParkingUseCase.Params("GCM-9998")
        val parking = Parking("2025-09-24T03:56:59.967+00:00", "GCM-9998", "68d36c")
        every { repository.doParking(params.plate) } returns flowOf(parking)

        // When
        val result = doParkingUseCase.invoke(params)

        // Then
        result.test {
            assertEquals(parking, expectItem())
            verify(exactly = 1) { repository.doParking(params.plate) }
            expectComplete()
        }
    }

    @Test
    fun `use cases's invoke should throw an exception when repository throws an exception`() = runBlocking {
        // Given
        val params = DoParkingUseCase.Params("")
        val exception = Throwable("Error")
        every { repository.doParking(params.plate) } returns flow { throw exception }

        // When
        val result = doParkingUseCase.invoke(params)

        // Then
        result.test {
            assertEquals(exception, expectError())
            verify(exactly = 1) { repository.doParking(params.plate) }
            expectNoEvents()
        }
    }
}