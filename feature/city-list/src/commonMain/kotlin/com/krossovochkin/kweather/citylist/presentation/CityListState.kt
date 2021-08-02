package com.krossovochkin.kweather.citylist.presentation

import com.krossovochkin.kweather.domain.City

sealed class CityListState {

    object Loading : CityListState()

    data class Data(
        val cityList: List<City>,
        val queryText: String,
        val cityNameHintText: String,
        val useCurrentLocationText: String
    ) : CityListState()

    data class Error(
        val error: Throwable
    ) : CityListState()
}
