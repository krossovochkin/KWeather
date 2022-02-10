package com.krossovochkin.kweather.weatherdetails.domain

import com.krossovochkin.kweather.domain.test.TestCityBuilder
import com.krossovochkin.kweather.storagecurrentcity.test.TestCurrentCityStorage
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class GetCurrentCityInteractorTest {

    private val storage = TestCurrentCityStorage()

    private val interactor: GetCurrentCityInteractor = GetCurrentCityInteractorImpl(
        currentCityStorage = storage
    )

    @Test
    fun `if current city not set then returns null`() {
        runTest {
            assertNull(interactor.get())
        }
    }

    @Test
    fun `if current city set then returns that city id`() {
        val city = TestCityBuilder().build()

        storage.setCity(city)

        runTest {
            assertEquals(
                expected = city,
                actual = interactor.get()
            )
        }
    }
}
