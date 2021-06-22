package com.krossovochkin.kweather.weatherdetails.domain

import com.krossovochkin.core.test.runBlockingTest
import com.krossovochkin.kweather.core.domain.TestCityIdBuilder
import com.krossovochkin.kweather.core.storage.TestCurrentCityIdStorage
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class GetCurrentCityIdInteractorTest {

    private val storage = TestCurrentCityIdStorage()

    private val interactor: GetCurrentCityIdInteractor = GetCurrentCityIdInteractorImpl(
        currentCityIdStorage = storage
    )

    @Test
    fun `if current city not set then returns null`() {
        runBlockingTest {
            assertNull(interactor.get())
        }
    }

    @Test
    fun `if current city set then returns that city id`() {
        val cityId = TestCityIdBuilder().build()

        storage.setCityId(cityId)

        runBlockingTest {
            assertEquals(
                expected = cityId,
                actual = interactor.get()
            )
        }
    }
}
