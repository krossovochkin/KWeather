package com.krossovochkin.kweather.core.router

sealed class RouterDestination(
    val route: String
) {

    object CityList : RouterDestination("city_list")

    object WeatherDetails : RouterDestination("weather_details")
}
