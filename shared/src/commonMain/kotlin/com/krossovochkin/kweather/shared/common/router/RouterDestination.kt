package com.krossovochkin.kweather.shared.common.router

sealed class RouterDestination {

    object Setup: RouterDestination()

    object CityList : RouterDestination()

    object WeatherDetails : RouterDestination()
}