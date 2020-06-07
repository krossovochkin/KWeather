package com.krossovochkin.kweather.shared.feature.citylist.domain

interface CityListRepository {

    suspend fun getCityList(
        query: String,
        limit: Int
    ): List<City>
}
