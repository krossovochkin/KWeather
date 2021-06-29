package com.krossovochkin.kweather.citylist.domain

import com.krossovochkin.kweather.service.storagecurrentcity.MutableCurrentCityIdStorage

interface SelectCityInteractor {

    suspend fun select(city: com.krossovochkin.kweather.domain.City)
}

class SelectCityInteractorImpl(
    private val currentCityStorage: MutableCurrentCityIdStorage
) : SelectCityInteractor {

    override suspend fun select(city: com.krossovochkin.kweather.domain.City) {
        currentCityStorage.saveCityId(city.id)
    }
}
