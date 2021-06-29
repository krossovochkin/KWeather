package com.krossovochkin.kweather.core.domain

private const val DEFAULT_CITY_NAME = "Minsk"

class TestCityBuilder {

    private var id: com.krossovochkin.kweather.domain.CityId = TestCityIdBuilder().build()
    private var name: String = DEFAULT_CITY_NAME

    fun setId(id: com.krossovochkin.kweather.domain.CityId) = apply {
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
