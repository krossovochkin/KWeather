package com.krossovochkin.kweather.citylist.data

import com.krossovochkin.kweather.core.CityListApi
import com.krossovochkin.kweather.domain.City

internal class NetworkCityListDatasource(
    private val api: CityListApi,
    private val mapper: CityListMapper
) : CityListDatasource {

    override suspend fun getCityList(query: String): List<City> {
        return api.getCityList(query)
            .list
            .map { mapper.map(it) }
    }
}
