package com.krossovochkin.kweather.citylist.data

import com.krossovochkin.kweather.citylist.domain.CityListRepository
import com.krossovochkin.kweather.domain.City

internal class CityListRepositoryImpl(
    private val cityListDatasource: CityListDatasource
) : CityListRepository {

    override suspend fun getCityList(query: String): List<City> {
        return cityListDatasource.getCityList(query)
    }
}
