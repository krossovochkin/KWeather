package com.krossovochkin.kweather.shared.feature.citylist.presentation

import com.krossovochkin.kweather.shared.feature.citylist.domain.City

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
