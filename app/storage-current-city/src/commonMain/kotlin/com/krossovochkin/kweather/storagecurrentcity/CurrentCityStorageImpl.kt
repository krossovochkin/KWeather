package com.krossovochkin.kweather.storagecurrentcity

import com.krossovochkin.kweather.domain.City
import com.krossovochkin.kweather.domain.CityId
import com.krossovochkin.kweather.domain.CityLocation
import com.krossovochkin.storage.Storage

private const val KEY_CURRENT_CITY_ID = "KEY_CURRENT_CITY_ID"
private const val KEY_CURRENT_CITY_NAME = "KEY_CURRENT_CITY_NAME"
private const val KEY_CURRENT_CITY_LATITUDE = "KEY_CURRENT_CITY_LATITUDE"
private const val KEY_CURRENT_CITY_LONGITUDE = "KEY_CURRENT_CITY_LONGITUDE"

internal class CurrentCityStorageImpl(
    private val storage: Storage,
) : MutableCurrentCityStorage {

    override suspend fun getCity(): City? {
        return runCatching {
            City(
                id = CityId(
                    id = storage.getInt(KEY_CURRENT_CITY_ID)!!
                ),
                name = storage.getString(KEY_CURRENT_CITY_NAME)!!,
                location = CityLocation(
                    latitude = storage.getDouble(KEY_CURRENT_CITY_LATITUDE)!!,
                    longitude = storage.getDouble(KEY_CURRENT_CITY_LONGITUDE)!!
                )
            )
        }.getOrNull()
    }

    override suspend fun saveCity(city: City) {
        storage.putInt(KEY_CURRENT_CITY_ID, city.id.id)
        storage.putString(KEY_CURRENT_CITY_NAME, city.name)
        storage.putDouble(KEY_CURRENT_CITY_LATITUDE, city.location.latitude)
        storage.putDouble(KEY_CURRENT_CITY_LONGITUDE, city.location.longitude)
    }
}
