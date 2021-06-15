package com.krossovochkin.kweather.citylist.domain

import com.krossovochkin.kweather.core.domain.City
import com.krossovochkin.kweather.core.storage.MutableCurrentCityStorage

interface SelectCityInteractor {

    suspend fun select(city: City)
}

class SelectCityInteractorImpl(
    private val currentCityStorage: MutableCurrentCityStorage
) : SelectCityInteractor {

    override suspend fun select(city: City) {
        currentCityStorage.saveCity(city)
    }
}
