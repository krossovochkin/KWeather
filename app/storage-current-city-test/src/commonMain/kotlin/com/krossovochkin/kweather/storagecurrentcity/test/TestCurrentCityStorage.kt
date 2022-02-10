package com.krossovochkin.kweather.storagecurrentcity.test

import com.krossovochkin.kweather.domain.City
import com.krossovochkin.kweather.storagecurrentcity.CurrentCityStorage
import kotlinx.coroutines.delay

class TestCurrentCityStorage(
    private val delayMillis: Long? = null
) : CurrentCityStorage {

    private var city: City? = null

    override suspend fun getCity(): City? {
        delayMillis?.let { delay(it) }
        return city
    }

    fun setCity(city: City) {
        this.city = city
    }
}
