package com.krossovochkin.kweather.domain.test

import com.krossovochkin.kweather.domain.CityId

private const val DEFAULT_CITY_ID = 100

class TestCityIdBuilder {

    private var id: Int = DEFAULT_CITY_ID

    fun setId(id: Int) = apply {
        this.id = id
    }

    fun build(): CityId {
        return CityId(id)
    }
}
