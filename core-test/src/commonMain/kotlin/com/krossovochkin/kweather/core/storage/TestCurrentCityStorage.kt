package com.krossovochkin.kweather.core.storage

import com.krossovochkin.kweather.core.domain.City

class TestCurrentCityStorage : CurrentCityStorage {

    private var city: City? = null

    override suspend fun getCity(): City? {
        return city
    }

    fun setCity(city: City) {
        this.city = city
    }
}
