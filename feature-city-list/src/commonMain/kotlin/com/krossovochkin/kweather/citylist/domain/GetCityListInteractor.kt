package com.krossovochkin.kweather.citylist.domain

import com.krossovochkin.kweather.core.domain.City

interface GetCityListInteractor {

    suspend fun get(query: String): List<City>
}

private const val CITY_LIST_SIZE = 30

class GetCityListInteractorImpl(
    private val cityListRepository: CityListRepository
) : GetCityListInteractor {

    override suspend fun get(query: String): List<City> {
        return cityListRepository.getCityList(query, CITY_LIST_SIZE)
    }
}
