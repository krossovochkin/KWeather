package com.krossovochkin.kweather.citylist.data

import com.krossovochkin.kweather.domain.City

interface CityListDatasource {

    suspend fun getCityList(query: String): List<City>
}
