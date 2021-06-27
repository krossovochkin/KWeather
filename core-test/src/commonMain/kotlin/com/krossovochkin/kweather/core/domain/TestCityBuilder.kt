package com.krossovochkin.kweather.core.domain

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

    fun build(): City {
        return City(
            id = id,
            name = name
        )
    }
}