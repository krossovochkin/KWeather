package com.krossovochkin.kweather.weatherdetails.domain

import com.krossovochkin.kweather.core.domain.TestCityBuilder
import com.krossovochkin.kweather.domain.City
import com.krossovochkin.kweather.domain.WeatherDetails

class TestWeatherDetailsBuilder {

    private var city: City = TestCityBuilder().build()
    private var todayWeatherData = TestTodayWeatherDataBuilder().build()
    private var tomorrowWeatherData = TestTomorrowWeatherDataBuilder().build()
    private var weekWeatherData = listOf(
        TestDailyWeatherDataBuilder().build()
    )

    fun setCity(city: City) = apply {
        this.city = city
    }

    fun build(): WeatherDetails {
        return WeatherDetails(
            city = city,
            todayWeatherData = todayWeatherData,
            tomorrowWeatherData = tomorrowWeatherData,
            weekWeatherData = weekWeatherData
        )
    }
}
