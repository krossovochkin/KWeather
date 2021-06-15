package com.krossovochkin.kweather.citylist.presentation

import com.krossovochkin.kweather.core.domain.City

sealed class CityListAction {

    object Load : CityListAction()

    data class SelectCity(
        val city: City
    ) : CityListAction()

    data class ChangeCityNameQuery(
        val query: String
    ) : CityListAction()
}

sealed class CityListActionResult {

    data class Loaded(
        val cityList: List<City>
    ) : CityListActionResult()

    data class CityNameQueryChanged(
        val query: String
    ) : CityListActionResult()
}
