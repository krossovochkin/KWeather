package com.krossovochkin.kweather.weatherdetails.domain

import com.krossovochkin.kweather.domain.CityId
import com.krossovochkin.kweather.service.storagecurrentcity.CurrentCityIdStorage

interface GetCurrentCityIdInteractor {

    suspend fun get(): CityId?
}

internal class GetCurrentCityIdInteractorImpl(
    private val currentCityIdStorage: CurrentCityIdStorage
) : GetCurrentCityIdInteractor {

    override suspend fun get(): CityId? {
        return currentCityIdStorage.getCityId()
    }
}
