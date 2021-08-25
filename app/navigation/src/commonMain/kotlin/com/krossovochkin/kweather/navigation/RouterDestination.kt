package com.krossovochkin.kweather.navigation

sealed class RouterDestination(
    override val route: String
) : com.krossovochkin.navigation.RouterDestination {

    object CityList : RouterDestination("city_list")

    object WeatherDetails : RouterDestination("weather_details")
}
