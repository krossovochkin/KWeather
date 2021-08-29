package com.krossovochkin.kweather.weathermap.domain

import com.krossovochkin.kweather.domain.City
import com.krossovochkin.kweather.storagecurrentcity.CurrentCityStorage

interface GetCurrentCityInteractor {

    suspend fun get(): City?
}

internal class GetCurrentCityInteractorImpl(
    private val currentCityStorage: CurrentCityStorage
) : GetCurrentCityInteractor {

    override suspend fun get(): City? {
        return currentCityStorage.getCity()
    }
}
