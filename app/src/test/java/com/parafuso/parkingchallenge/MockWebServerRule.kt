package com.parafuso.parkingchallenge

import okhttp3.mockwebserver.MockWebServer
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class MockWebServerRule : TestWatcher() {

    val server = MockWebServer()

    fun getBaseUrl(): String = server.url("/").toString()

    override fun starting(description: Description?) {
        super.starting(description)
        // server.start(8080)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        server.shutdown()
    }
}
