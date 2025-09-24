package com.parafuso.parkingchallenge.parkingpay.domain

import app.cash.turbine.test
import com.parafuso.parkingchallenge.feature.parkingpay.domain.repository.ParkingPayRepository
import com.parafuso.parkingchallenge.feature.parkingpay.domain.usecase.PayParkingUseCase
import com.parafuso.parkingchallenge.feature.parkingpay.domain.usecase.PayParkingUseCaseImpl
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
class PayParkingUseCaseImplTest {

    private val repository: ParkingPayRepository = mockk(relaxed = true)
    private val payParkingUseCase: PayParkingUseCase = PayParkingUseCaseImpl(repository)

    @Test
    fun `use cases's invoke should return flow of nothing when repository return nothing`() = runBlocking {
        // Given
        val params = PayParkingUseCase.Params("GCM-9998")
        every { repository.payParking(params.plate) } returns flowOf(Unit)

        // When
        val result = payParkingUseCase.invoke(params)

        // Then
        result.test {
            assertEquals(Unit, expectItem())
            verify(exactly = 1) { repository.payParking(params.plate) }
            expectComplete()
        }
    }

    @Test
    fun `use case's invoke should throw an exception when repository throws an exception`() = runBlocking {
        // Given
        val params = PayParkingUseCase.Params("")
        val exception = Throwable("Error")
        every { repository.payParking(params.plate) } returns flow { throw exception }

        // When
        val result = payParkingUseCase.invoke(params)

        // Then
        result.test {
            assertEquals(exception, expectError())
            verify(exactly = 1) { repository.payParking(params.plate) }
            expectNoEvents()
        }
    }
}