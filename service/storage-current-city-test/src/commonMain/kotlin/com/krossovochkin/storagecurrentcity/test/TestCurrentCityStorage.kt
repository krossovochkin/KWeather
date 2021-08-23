package com.krossovochkin.storagecurrentcity.test

import com.krossovochkin.kweather.domain.City
import com.krossovochkin.kweather.service.storagecurrentcity.CurrentCityStorage

class TestCurrentCityStorage : CurrentCityStorage {

    private var city: City? = null

    override suspend fun getCity(): City? {
        return city
    }

    fun setCity(city: City) {
        this.city = city
    }
}
