package com.krossovochkin.kweather.service.storagecurrentcity

import com.krossovochkin.kweather.domain.CityId

interface CurrentCityIdStorage {

    suspend fun getCityId(): CityId?
}
