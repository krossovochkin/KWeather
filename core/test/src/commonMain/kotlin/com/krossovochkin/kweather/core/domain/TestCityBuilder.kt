package com.krossovochkin.kweather.core.domain

import com.krossovochkin.kweather.domain.City
import com.krossovochkin.kweather.domain.CityId
import com.krossovochkin.kweather.domain.Location

private const val DEFAULT_CITY_NAME = "Minsk"

class TestCityBuilder {

    private var id: CityId = TestCityIdBuilder().build()
    private var name: String = DEFAULT_CITY_NAME
    private var location: Location = TestLocationBuilder().build()

    fun setId(id: CityId) = apply {
        this.id = id
    }

    fun setName(name: String) = apply {
        this.name = name
    }

    fun setLocation(location: Location) = apply {
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
