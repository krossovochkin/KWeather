package com.krossovochkin.kweather.shared.common.router

sealed class RouterDestination(
    val route: String
) {

    object Setup : RouterDestination("setup")

    object CityList : RouterDestination("city_list")

    object WeatherDetails : RouterDestination("weather_details")
}
