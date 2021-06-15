package com.krossovochkin.kweather.weatherdetails.domain

import com.krossovochkin.kweather.core.domain.City

data class WeatherDetails(
    val city: City,
    val temperature: Int,
    val weatherConditionsImageUrl: String
)
