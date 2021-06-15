package com.krossovochkin.kweather.citylist.domain

import com.krossovochkin.kweather.core.domain.City

interface CityListRepository {

    suspend fun getCityList(
        query: String,
        limit: Int
    ): List<City>
}
