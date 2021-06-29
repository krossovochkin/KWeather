package com.krossovochkin.kweather.core.domain

import com.krossovochkin.kweather.domain.CityId

private const val DEFAULT_CITY_NAME = "Minsk"

class TestCityBuilder {

    private var id: CityId = TestCityIdBuilder().build()
    private var name: String = DEFAULT_CITY_NAME

    fun setId(id: CityId) = apply {
        this.id = id
    }

    fun setName(name: String) = apply {
        this.name = name
    }

    fun build(): com.krossovochkin.kweather.domain.City {
        return com.krossovochkin.kweather.domain.City(
            id = id,
            name = name
        )
    }
}
