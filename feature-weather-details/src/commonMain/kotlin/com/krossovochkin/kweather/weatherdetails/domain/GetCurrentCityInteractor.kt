package com.krossovochkin.kweather.weatherdetails.domain

import com.krossovochkin.kweather.core.domain.City
import com.krossovochkin.kweather.core.storage.CurrentCityStorage

interface GetCurrentCityInteractor {

    suspend fun get(): City?
}

class GetCurrentCityInteractorImpl(
    private val currentCityStorage: CurrentCityStorage
) : GetCurrentCityInteractor {

    override suspend fun get(): City? {
        return currentCityStorage.getCity()
    }
}
