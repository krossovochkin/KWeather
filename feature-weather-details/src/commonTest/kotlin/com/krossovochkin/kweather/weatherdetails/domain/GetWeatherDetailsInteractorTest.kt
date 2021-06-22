package com.krossovochkin.kweather.weatherdetails.domain

import com.krossovochkin.core.test.runBlockingTest
import com.krossovochkin.kweather.core.domain.TestCityBuilder
import com.krossovochkin.kweather.core.domain.TestCityIdBuilder
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
        val cityId = TestCityIdBuilder().build()

        runBlockingTest {
            assertFails {
                interactor.get(cityId)
            }
        }
    }

    @Test
    fun `if there is data for given city then returns that data`() {
        val cityId = TestCityIdBuilder().build()
        val weatherDetails = TestWeatherDetailsBuilder().build()
        weatherDetailsRepository.put(cityId, weatherDetails)

        runBlockingTest {
            assertEquals(
                expected = weatherDetails,
                actual = interactor.get(cityId)
            )
        }
    }
}
