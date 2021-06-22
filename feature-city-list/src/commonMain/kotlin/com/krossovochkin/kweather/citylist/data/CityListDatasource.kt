package com.krossovochkin.kweather.citylist.data

import com.krossovochkin.kweather.core.domain.City

interface CityListDatasource {

    suspend fun getCityList(query: String): List<City>
}
