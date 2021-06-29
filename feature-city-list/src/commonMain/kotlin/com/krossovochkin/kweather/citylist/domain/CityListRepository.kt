package com.krossovochkin.kweather.citylist.domain

import com.krossovochkin.kweather.domain.City

interface CityListRepository {

    suspend fun getCityList(query: String): List<City>
}
