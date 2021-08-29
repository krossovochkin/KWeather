package com.krossovochkin.kweather.weathermap.domain

import com.krossovochkin.kweather.domain.City

data class WeatherMapData(
    val mapTileUrls: List<List<String>>,
    val precipitationTileUrls: List<List<String>>,
    val zoom: Int,
    val city: City,
    val tileBounds: TileBounds,
)
