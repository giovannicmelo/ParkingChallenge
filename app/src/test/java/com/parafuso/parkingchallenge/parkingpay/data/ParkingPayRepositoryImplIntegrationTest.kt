package com.parafuso.parkingchallenge.parkingpay.data

import app.cash.turbine.test
import com.parafuso.parkingchallenge.MockWebServerRule
import com.parafuso.parkingchallenge.core.data.model.ApiException
import com.parafuso.parkingchallenge.core.data.remote.createRetrofit
import com.parafuso.parkingchallenge.feature.parkingpay.data.api.ParkingPayService
import com.parafuso.parkingchallenge.feature.parkingpay.data.datasource.ParkingPayRemoteDataSourceImpl
import com.parafuso.parkingchallenge.feature.parkingpay.data.repository.ParkingPayRepositoryImpl
import com.parafuso.parkingchallenge.feature.parkingpay.domain.repository.ParkingPayRepository
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import kotlin.time.ExperimentalTime

@ExperimentalTime
class ParkingPayRepositoryImplIntegrationTest {

    @get:Rule
    private val mockWebServerRule = MockWebServerRule()
    private val retrofit = createRetrofit(mockWebServerRule.getBaseUrl())
    private val repository: ParkingPayRepository = createRepository(retrofit)
    private val mockWebServer: MockWebServer = mockWebServerRule.server

    @Test
    fun `payParking should return nothing when request is successful`() = runBlocking {
        // Given
        val mockResponse = MockResponse().setResponseCode(204)
        mockWebServer.enqueue(mockResponse)

        // When
        val result = repository.payParking("GCM-9998")

        // Then
        result.test {
            assertEquals(Unit, expectItem())
            assertEquals(1, mockWebServer.requestCount)
            expectComplete()
        }
    }

    @Test
    fun `payParking should throw an api error when request fails`() = runBlocking {
        // Given
        val expectedError = ApiException(422, "Client Error")
        val jsonBody = "{\"errors\": {\"plate\": [\"Client Error\"]}}"
        val mockResponse = MockResponse().setResponseCode(422).setBody(jsonBody)
        mockWebServer.enqueue(mockResponse)

        // When
        val result = repository.payParking("")

        // Then
        result.test {
            assertEquals(expectedError.message, expectError().message)
            assertEquals(1, mockWebServer.requestCount)
            expectNoEvents()
        }
    }

    @Test
    fun `payParking should throw a generic error when request fails`() = runBlocking {
        // Given
        val mockResponse = MockResponse().setResponseCode(500).setBody("Server Error")
        mockWebServer.enqueue(mockResponse)

        // When
        val result = repository.payParking("")

        // Then
        result.test {
            assert(expectError() is Throwable)
            assertEquals(1, mockWebServer.requestCount)
            expectNoEvents()
        }
    }

    private fun createRepository(retrofit: Retrofit): ParkingPayRepository {
        val api = retrofit.create(ParkingPayService::class.java)
        val dataSource = ParkingPayRemoteDataSourceImpl(api)
        return ParkingPayRepositoryImpl(dataSource)
    }
}