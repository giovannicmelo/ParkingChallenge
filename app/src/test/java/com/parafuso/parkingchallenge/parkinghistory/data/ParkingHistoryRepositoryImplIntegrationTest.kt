package com.parafuso.parkingchallenge.parkinghistory.data

import app.cash.turbine.test
import com.parafuso.parkingchallenge.MockWebServerRule
import com.parafuso.parkingchallenge.core.data.model.ApiException
import com.parafuso.parkingchallenge.core.data.remote.createRetrofit
import com.parafuso.parkingchallenge.feature.parkinghistory.data.api.ParkingHistoryService
import com.parafuso.parkingchallenge.feature.parkinghistory.data.datasource.ParkingHistoryRemoteDataSourceImpl
import com.parafuso.parkingchallenge.feature.parkinghistory.data.mapper.ParkingHistoryMapper
import com.parafuso.parkingchallenge.feature.parkinghistory.data.repository.ParkingHistoryRepositoryImpl
import com.parafuso.parkingchallenge.feature.parkinghistory.domain.model.ParkingHistory
import com.parafuso.parkingchallenge.feature.parkinghistory.domain.repository.ParkingHistoryRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import kotlin.time.ExperimentalTime

@ExperimentalTime
class ParkingHistoryRepositoryImplIntegrationTest {

    @get:Rule
    private val mockWebServerRule = MockWebServerRule()
    private val retrofit = createRetrofit(mockWebServerRule.getBaseUrl())
    private val repository: ParkingHistoryRepository = createRepository(retrofit)
    private val mockWebServer: MockWebServer = mockWebServerRule.server

    @Test
    fun `getParkingHistory should return a list of parking history when plate is valid`() = runBlocking {
        // Given
        val jsonBody = "[{\"time\": \"13 hours 42 minutes\",\"paid\": true,\"left\": true,\"plate\": \"GCM-9998\",\"reservation\": \"68d348\"}]"
        val expectedParkingHistory = listOf(
            ParkingHistory(
                left = true,
                paid = true,
                plate = "GCM-9998",
                reservation = "68d348",
                time = "13 hours 42 minutes"
            )
        )
        val mockResponse = MockResponse().setResponseCode(200).setBody(jsonBody)
        mockWebServer.enqueue(mockResponse)

        // When
        val result = repository.getParkingHistory("GCM-9998")

        // Then
        result.test {
            assertEquals(expectedParkingHistory, expectItem())
            assertEquals(1, mockWebServer.requestCount)
            expectComplete()
        }
    }

    @Test
    fun `getParkingHistory should throw an api error when plate is invalid`() = runBlocking {
        // Given
        val expectedError = ApiException(422, "Client Error")
        val jsonBody = "{\"errors\": {\"plate\": [\"Client Error\"]}}"
        val mockResponse = MockResponse().setResponseCode(422).setBody(jsonBody)
        mockWebServer.enqueue(mockResponse)

        // When
        val result = repository.getParkingHistory("")

        // Then
        result.test {
            assertEquals(expectedError.message, expectError().message)
            assertEquals(1, mockWebServer.requestCount)
            expectNoEvents()
        }
    }

    @Test
    fun `getParkingHistory should throw a generic error when request fails`() = runBlocking {
        // Given
        val mockResponse = MockResponse().setResponseCode(500).setBody("Server Error")
        mockWebServer.enqueue(mockResponse)

        // When
        val result = repository.getParkingHistory("")

        // Then
        result.test {
            assert(expectError() is Throwable)
            assertEquals(1, mockWebServer.requestCount)
            expectNoEvents()
        }
    }

    private fun createRepository(retrofit: Retrofit): ParkingHistoryRepository {
        val api = retrofit.create(ParkingHistoryService::class.java)
        val mapper = ParkingHistoryMapper()
        val dataSource = ParkingHistoryRemoteDataSourceImpl(api, mapper)
        return ParkingHistoryRepositoryImpl(dataSource)
    }
}