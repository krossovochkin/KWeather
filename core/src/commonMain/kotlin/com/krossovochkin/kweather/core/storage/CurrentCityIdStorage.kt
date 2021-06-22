package com.krossovochkin.kweather.core.storage

import com.krossovochkin.kweather.core.domain.CityId

interface CurrentCityIdStorage {

    suspend fun getCityId(): CityId?
}

interface MutableCurrentCityIdStorage : CurrentCityIdStorage {

    suspend fun saveCityId(cityId: CityId)
}

private const val KEY_CURRENT_CITY_ID = "KEY_CURRENT_CITY_ID"

class CurrentCityIdStorageImpl(
    private val storage: Storage,
) : MutableCurrentCityIdStorage {

    override suspend fun getCityId(): CityId? {
        return storage.getInt(KEY_CURRENT_CITY_ID)
            ?.let(::CityId)
    }

    override suspend fun saveCityId(cityId: CityId) {
        storage.putInt(KEY_CURRENT_CITY_ID, cityId.id)
    }
}
