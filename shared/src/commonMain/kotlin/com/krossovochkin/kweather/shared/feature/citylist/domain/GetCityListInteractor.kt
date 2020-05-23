package com.krossovochkin.kweather.shared.feature.citylist.domain

interface GetCityListInteractor {

    suspend fun get(query: String): List<City>
}

class GetCityListInteractorImpl(
    private val cityListRepository: CityListRepository
): GetCityListInteractor {

    override suspend fun get(query: String): List<City> {
        return cityListRepository.getCityList(query)
    }
}