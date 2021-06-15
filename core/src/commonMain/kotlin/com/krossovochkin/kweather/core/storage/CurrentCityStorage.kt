package com.krossovochkin.kweather.core.storage

import com.krossovochkin.kweather.core.domain.City
import com.krossovochkin.kweather.core.storage.citylist.CityListMapper

interface CurrentCityStorage {

    suspend fun getCity(): City?
}

interface MutableCurrentCityStorage : CurrentCityStorage {

    suspend fun saveCity(city: City)
}

private const val KEY_CURRENT_CITY = "KEY_CURRENT_CITY"

class CurrentCityStorageImpl(
    private val storage: Storage,
    private val cityListMapper: CityListMapper,
    private val citySerializer: CitySerializer = CitySerializer
) : MutableCurrentCityStorage {

    override suspend fun getCity(): City? {
        return storage.getString(KEY_CURRENT_CITY)
            ?.let(citySerializer::fromJson)
            ?.let(cityListMapper::map)
    }

    override suspend fun saveCity(city: City) {
        city
            .let(cityListMapper::map)
            .let(citySerializer::toJson)
            .let {
                storage.putString(KEY_CURRENT_CITY, it)
            }
    }
}
