package com.krossovochkin.kweather.weatherdetails.domain

import com.krossovochkin.test.runBlockingTest
import com.krossovochkin.domain.test.TestCityBuilder
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class GetWeatherDetailsInteractorTest {

    private val weatherDetailsRepository = TestWeatherDetailsRepository()

    private val interactor: GetWeatherDetailsInteractor = GetWeatherDetailsInteractorImpl(
        weatherDetailsRepository = weatherDetailsRepository
    )

    @Test
    fun `if no data for given city then throws exception`() {
        val city = TestCityBuilder().build()

        runBlockingTest {
            assertFails {
                interactor.get(city)
            }
        }
    }

    @Test
    fun `if there is data for given city then returns that data`() {
        val city = TestCityBuilder().build()
        val weatherDetails = TestWeatherDetailsBuilder().build()
        weatherDetailsRepository.put(city, weatherDetails)

        runBlockingTest {
            assertEquals(
                expected = weatherDetails,
                actual = interactor.get(city)
            )
        }
    }
}
