package com.krossovochkin.kweather.shared.feature.citylist.domain

interface GetCityListInteractor {

    suspend fun get(query: String): List<City>
}

private const val CITY_LIST_SIZE = 30

class GetCityListInteractorImpl(
    private val cityListRepository: CityListRepository
): GetCityListInteractor {

    override suspend fun get(query: String): List<City> {
        return cityListRepository.getCityList(query, CITY_LIST_SIZE)
    }
}