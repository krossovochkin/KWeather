package com.krossovochkin.kweather.service.storagecurrentcity

import com.krossovochkin.kweather.domain.CityId
import com.krossovochkin.storage.Storage

interface CurrentCityIdStorage {

    suspend fun getCityId(): CityId?
}


