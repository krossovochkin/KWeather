package com.krossovochkin.kweather.citylist.domain

import com.krossovochkin.kweather.core.domain.City
import com.krossovochkin.kweather.core.storage.MutableCurrentCityIdStorage

interface SelectCityInteractor {

    suspend fun select(city: City)
}

class SelectCityInteractorImpl(
    private val currentCityStorage: MutableCurrentCityIdStorage
) : SelectCityInteractor {

    override suspend fun select(city: City) {
        currentCityStorage.saveCityId(city.id)
    }
}
