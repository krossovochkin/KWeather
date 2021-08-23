package com.krossovochkin.kweather.citylist.data

import com.krossovochkin.kweather.core.dto.CityListDto.CityDto
import com.krossovochkin.kweather.core.dto.LocationDto
import com.krossovochkin.kweather.domain.City
import com.krossovochkin.kweather.domain.CityId
import com.krossovochkin.kweather.domain.CityLocation

internal interface CityListMapper {

    fun map(dto: CityDto): City

    fun map(city: City): CityDto
}

internal class CityListMapperImpl : CityListMapper {

    override fun map(dto: CityDto): City {
        return with(dto) {
            City(
                id = CityId(id),
                name = name,
                location = CityLocation(
                    latitude = location.latitude,
                    longitude = location.longitude
                )
            )
        }
    }

    override fun map(city: City): CityDto {
        return with(city) {
            CityDto(
                id = id.id,
                name = name,
                location = LocationDto(
                    latitude = location.latitude,
                    longitude = location.longitude
                )
            )
        }
    }
}
