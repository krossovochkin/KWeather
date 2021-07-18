package com.krossovochkin.kweather.weatherdetails.domain

import com.krossovochkin.kweather.core.domain.TestCityBuilder
import com.krossovochkin.kweather.domain.City
import com.krossovochkin.kweather.domain.WeatherDetails

class TestWeatherDetailsBuilder {

    private var city: City = TestCityBuilder().build()
    private var currentWeatherData: WeatherDetails.WeatherData = TestWeatherDataBuilder().build()

    fun setCity(city: City) = apply {
        this.city = city
    }

    fun setCurrentWeatherData(currentWeatherData: WeatherDetails.WeatherData) = apply {
        this.currentWeatherData = currentWeatherData
    }

    fun build(): WeatherDetails {
        return WeatherDetails(
            city = city,
            currentWeatherData = currentWeatherData
        )
    }
}
