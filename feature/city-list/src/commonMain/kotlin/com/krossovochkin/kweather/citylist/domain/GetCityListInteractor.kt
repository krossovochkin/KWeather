package com.krossovochkin.kweather.citylist.domain

import com.krossovochkin.kweather.domain.City

interface GetCityListInteractor {

    suspend fun get(query: String): List<City>
}

private const val QUERY_MIN_LENGTH = 3

internal class GetCityListInteractorImpl(
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
