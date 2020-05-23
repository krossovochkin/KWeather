package com.krossovochkin.kweather.shared.feature.weatherdetails.domain

import com.krossovochkin.kweather.shared.common.storage.CurrentCityStorage
import com.krossovochkin.kweather.shared.feature.citylist.domain.City

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

