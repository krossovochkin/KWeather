package com.krossovochkin.kweather.weatherdetails.domain

import com.krossovochkin.kweather.domain.City

data class WeatherDetails(
    val city: City,
    val temperature: Int,
    val weatherConditionsImageUrl: String
)
