package com.krossovochkin.kweather.citylist.domain

import com.krossovochkin.kweather.core.domain.City

interface GetCityListInteractor {

    suspend fun get(query: String): List<City>
}

private const val QUERY_MIN_LENGTH = 3

class GetCityListInteractorImpl(
    private val cityListRepository: CityListRepository
) : GetCityListInteractor {

    override suspend fun get(query: String): List<City> {
        return if (query.length >= QUERY_MIN_LENGTH) {
            cityListRepository.getCityList(query)
        } else {
            emptyList()
        }
    }
}
