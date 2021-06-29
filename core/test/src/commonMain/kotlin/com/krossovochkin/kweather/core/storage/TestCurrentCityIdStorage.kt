package com.krossovochkin.kweather.core.storage

import com.krossovochkin.kweather.domain.CityId

class TestCurrentCityIdStorage : CurrentCityIdStorage {

    private var id: com.krossovochkin.kweather.domain.CityId? = null

    override suspend fun getCityId(): com.krossovochkin.kweather.domain.CityId? {
        return id
    }

    fun setCityId(id: com.krossovochkin.kweather.domain.CityId) {
        this.id = id
    }
}
