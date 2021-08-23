package com.krossovochkin.kweather.storagecurrentcity

import com.krossovochkin.kweather.domain.City

interface CurrentCityStorage {

    suspend fun getCity(): City?
}
