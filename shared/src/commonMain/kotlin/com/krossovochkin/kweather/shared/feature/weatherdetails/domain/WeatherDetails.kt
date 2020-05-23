package com.krossovochkin.kweather.shared.feature.weatherdetails.domain

import com.krossovochkin.kweather.shared.feature.citylist.domain.City

data class WeatherDetails(
    val city: City,
    val temperature: Int,
    val weatherConditionsImageUrl: String
)
