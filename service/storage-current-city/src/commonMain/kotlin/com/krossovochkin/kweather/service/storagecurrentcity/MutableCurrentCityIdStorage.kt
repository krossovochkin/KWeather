package com.krossovochkin.kweather.service.storagecurrentcity

import com.krossovochkin.kweather.domain.CityId

interface MutableCurrentCityIdStorage : CurrentCityIdStorage {

    suspend fun saveCityId(cityId: CityId)
}
