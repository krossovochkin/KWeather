package com.krossovochkin.kweather.domain.test

import com.krossovochkin.kweather.domain.City
import com.krossovochkin.kweather.domain.CityId
import com.krossovochkin.kweather.domain.CityLocation

private const val DEFAULT_CITY_NAME = "Minsk"

class TestCityBuilder {

    private var id: CityId = TestCityIdBuilder().build()
    private var name: String = DEFAULT_CITY_NAME
    private var location: CityLocation = TestCityLocationBuilder().build()

    fun setId(id: CityId) = apply {
        this.id = id
    }

    fun setName(name: String) = apply {
        this.name = name
    }

    fun setCityLocation(location: CityLocation) = apply {
        this.location = location
    }

    fun build(): City {
        return City(
            id = id,
            name = name,
            location = location
        )
    }
}
