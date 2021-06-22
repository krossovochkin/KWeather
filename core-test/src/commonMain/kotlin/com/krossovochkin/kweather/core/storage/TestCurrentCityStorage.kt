package com.krossovochkin.kweather.core.storage

import com.krossovochkin.kweather.core.domain.CityId

class TestCurrentCityIdStorage : CurrentCityIdStorage {

    private var id: CityId? = null

    override suspend fun getCityId(): CityId? {
        return id
    }

    fun setCityId(id: CityId) {
        this.id = id
    }
}
