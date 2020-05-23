package com.krossovochkin.kweather.shared.feature.citylist.domain

import com.krossovochkin.kweather.shared.common.storage.MutableCurrentCityStorage

interface SelectCityInteractor {

    suspend fun select(city: City)
}

class SelectCityInteractorImpl(
    private val currentCityStorage: MutableCurrentCityStorage
): SelectCityInteractor {

    override suspend fun select(city: City) {
        currentCityStorage.saveCity(city)
    }
}