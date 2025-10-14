package com.parafuso.parkingchallenge.core.domain

import app.cash.turbine.test
import com.parafuso.parkingchallenge.core.domain.usecase.ValidatePlateUseCase
import com.parafuso.parkingchallenge.core.domain.usecase.ValidatePlateUseCaseImpl
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
class ValidatePlateUseCaseImplTest {

    private val validatePlateUseCase: ValidatePlateUseCase = ValidatePlateUseCaseImpl()

    @Test
    fun `use cases's invoke should return true when plate is valid`() = runBlocking {
        // Given
        val params = ValidatePlateUseCase.Params("ABC-1234")

        // When
        val result = validatePlateUseCase.invoke(params)

        // Then
        result.test {
            assertEquals(true, expectItem())
            expectComplete()
        }
    }

    @Test
    fun `use cases's invoke should return false when plate is invalid`() = runBlocking {
        // Given
        val params = ValidatePlateUseCase.Params("ABC1234")

        // When
        val result = validatePlateUseCase.invoke(params)

        // Then
        result.test {
            assertEquals(false, expectItem())
            expectComplete()
        }
    }
}