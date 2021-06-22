package com.krossovochkin.kweather.weatherdetails.domain

import com.krossovochkin.kweather.core.domain.CityId
import com.krossovochkin.kweather.core.storage.CurrentCityIdStorage

interface GetCurrentCityIdInteractor {

    suspend fun get(): CityId?
}

class GetCurrentCityIdInteractorImpl(
    private val currentCityIdStorage: CurrentCityIdStorage
) : GetCurrentCityIdInteractor {

    override suspend fun get(): CityId? {
        return currentCityIdStorage.getCityId()
    }
}
