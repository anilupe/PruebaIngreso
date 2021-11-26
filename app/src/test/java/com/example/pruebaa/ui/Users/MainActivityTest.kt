package com.example.pruebaa.ui.Users

import com.example.pruebaa.Commons.Commons
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import java.net.HttpURLConnection

class MainActivityTest {
    private val testSample: Commons = Commons()

    companion object{
        @JvmField
        @RegisterExtension

        val jettyExtension: JettyExtension = JettyExtension()

    }
    @Test
    fun testSum() {
       val url= jettyExtension.serverUri.resolve(testSample.urlBase+"users").toURL()
        val connection=url.openConnection() as HttpURLConnection
        val response = connection.responseCode
        Assertions.assertEquals(200, response)
    }
}