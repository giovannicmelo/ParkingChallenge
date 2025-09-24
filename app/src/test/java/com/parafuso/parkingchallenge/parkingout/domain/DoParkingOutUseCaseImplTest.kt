package com.parafuso.parkingchallenge.parkingout.domain

import app.cash.turbine.test
import com.parafuso.parkingchallenge.feature.parkingout.domain.repository.ParkingOutRepository
import com.parafuso.parkingchallenge.feature.parkingout.domain.usecase.DoParkingOutUseCase
import com.parafuso.parkingchallenge.feature.parkingout.domain.usecase.DoParkingOutUseCaseImpl
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
class DoParkingOutUseCaseImplTest {

    private val repository: ParkingOutRepository = mockk(relaxed = true)
    private val doParkingOutUseCase: DoParkingOutUseCase = DoParkingOutUseCaseImpl(repository)

    @Test
    fun `use cases's invoke should return flow of nothing when repository return nothing`() = runBlocking {
        // Given
        val params = DoParkingOutUseCase.Params("GCM-9998")
        every { repository.doParkingOut(params.plate) } returns flowOf(Unit)

        // When
        val result = doParkingOutUseCase.invoke(params)

        // Then
        result.test {
            assertEquals(Unit, expectItem())
            verify(exactly = 1) { repository.doParkingOut(params.plate) }
            expectComplete()
        }
    }

    @Test
    fun `use case's invoke should throw an exception when repository throws an exception`() = runBlocking {
        // Given
        val params = DoParkingOutUseCase.Params("")
        val exception = Throwable("Error")
        every { repository.doParkingOut(params.plate) } returns flow { throw exception }

        // When
        val result = doParkingOutUseCase.invoke(params)

        // Then
        result.test {
            assertEquals(exception, expectError())
            verify(exactly = 1) { repository.doParkingOut(params.plate) }
            expectNoEvents()
        }
    }
}