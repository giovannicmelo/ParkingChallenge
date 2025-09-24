package com.parafuso.parkingchallenge.parking.data

import app.cash.turbine.test
import com.parafuso.parkingchallenge.core.data.model.ApiException
import com.parafuso.parkingchallenge.core.data.remote.createRetrofit
import com.parafuso.parkingchallenge.feature.parking.data.api.ParkingService
import com.parafuso.parkingchallenge.feature.parking.data.datasource.ParkingRemoteDataSourceImpl
import com.parafuso.parkingchallenge.feature.parking.data.mapper.ParkingMapper
import com.parafuso.parkingchallenge.feature.parking.data.repository.ParkingRepositoryImpl
import com.parafuso.parkingchallenge.feature.parking.domain.model.Parking
import com.parafuso.parkingchallenge.feature.parking.domain.repository.ParkingRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Test
import retrofit2.Retrofit
import kotlin.time.ExperimentalTime

@ExperimentalTime
class ParkingRepositoryImplIntegrationTest {

    private val mockWebServer = MockWebServer()
    private val baseUrl = mockWebServer.url("/").toString()
    private val retrofit = createRetrofit(baseUrl)
    private val repository: ParkingRepository = createRepository(retrofit)

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `doParking should return a parking when request is successful`() = runBlocking {
        // Given
        val jsonBody = "{\"reservation\": \"68d36c\",\"plate\": \"GCM-9998\",\"entered_at\":\"2025-09-24T03:56:59.967+00:00\"}"
        val expectedParking = Parking("2025-09-24T03:56:59.967+00:00", "GCM-9998", "68d36c")
        val mockResponse = MockResponse().setResponseCode(200).setBody(jsonBody)
        mockWebServer.enqueue(mockResponse)

        // When
        val result = repository.doParking("GCM-9998")

        // Then
        result.test {
            assertEquals(expectedParking, expectItem())
            assertEquals(1, mockWebServer.requestCount)
            expectComplete()
        }
    }

    @Test
    fun `doParking should throw an api error when request fails`() = runBlocking {
        // Given
        val expectedError = ApiException(422, "not found")
        val jsonBody = "{\"errors\": {\"plate\": [\"not found\"]}}"
        val mockResponse = MockResponse().setResponseCode(422).setBody(jsonBody)
        mockWebServer.enqueue(mockResponse)

        // When
        val result = repository.doParking("")

        // Then
        result.test {
            assertEquals(expectedError.message, expectError().message)
            assertEquals(1, mockWebServer.requestCount)
            expectNoEvents()
        }
    }

    @Test
    fun `doParking should throw a generic error when request fails`() = runBlocking {
        // Given
        val mockResponse = MockResponse().setResponseCode(500).setBody("Server Error")
        mockWebServer.enqueue(mockResponse)

        // When
        val result = repository.doParking("")

        // Then
        result.test {
            assert(expectError() is Throwable)
            assertEquals(1, mockWebServer.requestCount)
            expectNoEvents()
        }
    }

    private fun createRepository(retrofit: Retrofit): ParkingRepository {
        val api = retrofit.create(ParkingService::class.java)
        val mapper = ParkingMapper()
        val dataSource = ParkingRemoteDataSourceImpl(api, mapper)
        return ParkingRepositoryImpl(dataSource)
    }
}