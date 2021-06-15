package com.krossovochkin.kweather.citylist.presentation

import com.krossovochkin.kweather.core.domain.City

sealed class CityListState {

    object Loading : CityListState()

    data class Data(
        val cityList: List<City>,
        val queryText: String,
        val cityNameHintText: String
    ) : CityListState()

    data class Error(
        val error: Throwable
    ) : CityListState()
}
