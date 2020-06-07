package com.krossovochkin.kweather.shared.feature.citylist.data

import com.krossovochkin.kweather.shared.common.domain.CityId
import com.krossovochkin.kweather.shared.common.storage.CityDto
import com.krossovochkin.kweather.shared.feature.citylist.domain.City

interface CityListMapper {

    fun map(dto: CityDto): City

    fun map(city: City): CityDto
}

class CityListMapperImpl : CityListMapper {

    override fun map(dto: CityDto): City {
        return with(dto) {
            City(
                id = CityId(id),
                name = name
            )
        }
    }

    override fun map(city: City): CityDto {
        return with(city) {
            CityDto(
                id = id.id,
                name = name
            )
        }
    }
}
