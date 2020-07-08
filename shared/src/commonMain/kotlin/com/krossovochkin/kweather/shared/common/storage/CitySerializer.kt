package com.krossovochkin.kweather.shared.common.storage

import kotlinx.serialization.builtins.list
import kotlinx.serialization.json.Json

object CitySerializer {

    private val json = Json { ignoreUnknownKeys = true }
    private val serializer = CityDto.serializer()
    private val listSerializer = serializer.list

    fun listFromJson(text: String): List<CityDto> {
        return json.parse(listSerializer, text)
    }

    fun listToJson(cityList: List<CityDto>): String {
        return json.toJson(listSerializer, cityList).toString()
    }

    fun fromJson(text: String): CityDto {
        return json.parse(serializer, text)
    }

    fun toJson(cityDto: CityDto): String {
        return json.toJson(serializer, cityDto).toString()
    }
}
