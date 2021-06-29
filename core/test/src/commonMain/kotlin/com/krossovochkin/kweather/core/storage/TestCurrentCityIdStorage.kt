package com.krossovochkin.kweather.core.storage

import com.krossovochkin.kweather.domain.CityId
import com.krossovochkin.kweather.service.storagecurrentcity.CurrentCityIdStorage

class TestCurrentCityIdStorage : CurrentCityIdStorage {

    private var id: CityId? = null

    override suspend fun getCityId(): CityId? {
        return id
    }

    fun setCityId(id: CityId) {
        this.id = id
    }
}
