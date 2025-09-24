package com.parafuso.parkingchallenge.parkingout.data

import app.cash.turbine.test
import com.parafuso.parkingchallenge.MockWebServerRule
import com.parafuso.parkingchallenge.core.data.model.ApiException
import com.parafuso.parkingchallenge.core.data.remote.createRetrofit
import com.parafuso.parkingchallenge.feature.parkingout.data.api.ParkingOutService
import com.parafuso.parkingchallenge.feature.parkingout.data.datasource.ParkingOutRemoteDataSourceImpl
import com.parafuso.parkingchallenge.feature.parkingout.data.repository.ParkingOutRepositoryImpl
import com.parafuso.parkingchallenge.feature.parkingout.domain.repository.ParkingOutRepository
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import kotlin.time.ExperimentalTime

@ExperimentalTime
class ParkingOutRepositoryImplIntegrationTest {

    @get:Rule
    private val mockWebServerRule = MockWebServerRule()
    private val retrofit = createRetrofit(mockWebServerRule.getBaseUrl())
    private val repository: ParkingOutRepository = createRepository(retrofit)
    private val mockWebServer: MockWebServer = mockWebServerRule.server

    @Test
    fun `doParkingOut should return nothing when request is successful`() = runBlocking {
        // Given
        val mockResponse = MockResponse().setResponseCode(204)
        mockWebServer.enqueue(mockResponse)

        // When
        val result = repository.doParkingOut("GCM-9998")

        // Then
        result.test {
            assertEquals(Unit, expectItem())
            assertEquals(1, mockWebServer.requestCount)
            expectComplete()
        }
    }

    @Test
    fun `doParkingOut should throw an api error when request fails`() = runBlocking {
        // Given
        val expectedError = ApiException(422, "Client Error")
        val jsonBody = "{\"errors\": {\"plate\": [\"Client Error\"]}}"
        val mockResponse = MockResponse().setResponseCode(422).setBody(jsonBody)
        mockWebServer.enqueue(mockResponse)

        // When
        val result = repository.doParkingOut("")

        // Then
        result.test {
            assertEquals(expectedError.message, expectError().message)
            assertEquals(1, mockWebServer.requestCount)
            expectNoEvents()
        }
    }

    @Test
    fun `doParkingOut should throw a generic error when request fails`() = runBlocking {
        // Given
        val mockResponse = MockResponse().setResponseCode(500).setBody("Server Error")
        mockWebServer.enqueue(mockResponse)

        // When
        val result = repository.doParkingOut("")

        // Then
        result.test {
            assert(expectError() is Throwable)
            assertEquals(1, mockWebServer.requestCount)
            expectNoEvents()
        }
    }

    private fun createRepository(retrofit: Retrofit): ParkingOutRepository {
        val api = retrofit.create(ParkingOutService::class.java)
        val dataSource = ParkingOutRemoteDataSourceImpl(api)
        return ParkingOutRepositoryImpl(dataSource)
    }
}