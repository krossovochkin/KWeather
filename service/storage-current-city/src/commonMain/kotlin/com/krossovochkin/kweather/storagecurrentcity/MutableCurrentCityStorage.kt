package com.krossovochkin.kweather.storagecurrentcity

import com.krossovochkin.kweather.domain.City

interface MutableCurrentCityStorage : CurrentCityStorage {

    suspend fun saveCity(city: City)
}
