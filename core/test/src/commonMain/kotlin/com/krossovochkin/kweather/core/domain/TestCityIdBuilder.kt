package com.krossovochkin.kweather.core.domain

private const val DEFAULT_CITY_ID = 100

class TestCityIdBuilder {

    private var id: Int = DEFAULT_CITY_ID

    fun setId(id: Int) = apply {
        this.id = id
    }

    fun build(): com.krossovochkin.kweather.domain.CityId {
        return com.krossovochkin.kweather.domain.CityId(id)
    }
}
