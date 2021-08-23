package com.krossovochkin.kweather.citylist.domain

import com.krossovochkin.kweather.domain.City
import com.krossovochkin.kweather.storagecurrentcity.MutableCurrentCityStorage

interface SelectCityInteractor {

    suspend fun select(city: City)
}

internal class SelectCityInteractorImpl(
    private val currentCityStorage: MutableCurrentCityStorage
) : SelectCityInteractor {

    override suspend fun select(city: City) {
        currentCityStorage.saveCity(city)
    }
}
