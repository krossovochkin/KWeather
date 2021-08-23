package com.krossovochkin.kweather.weatherdetails.domain

import com.krossovochkin.kweather.domain.test.TestCityBuilder
import com.krossovochkin.kweather.domain.City
import com.krossovochkin.kweather.domain.WeatherDetails

class TestWeatherDetailsBuilder {

    private var city: City = TestCityBuilder().build()
    private var todayWeatherData = TestOneDayWeatherDataBuilder().build()
    private var tomorrowWeatherData = TestOneDayWeatherDataBuilder().build()
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
