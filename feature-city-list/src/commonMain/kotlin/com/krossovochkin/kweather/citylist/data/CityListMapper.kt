package com.krossovochkin.kweather.citylist.data

import com.krossovochkin.kweather.core.domain.City
import com.krossovochkin.kweather.core.domain.CityId
import com.krossovochkin.kweather.core.dto.CityListDto.CityDto

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
