package com.krossovochkin.kweather.service.storagecurrentcity

import com.krossovochkin.kweather.domain.City

interface CurrentCityStorage {

    suspend fun getCity(): City?
}
