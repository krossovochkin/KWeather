package com.krossovochkin.kweather.shared.common.storage

import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

object CitySerializer {

    private val json = Json { ignoreUnknownKeys = true }
    private val serializer = CityDto.serializer()
    private val listSerializer = ListSerializer(serializer)

    fun listFromJson(text: String): List<CityDto> {
        return json.decodeFromString(listSerializer, text)
    }

    fun listToJson(cityList: List<CityDto>): String {
        return json.encodeToString(listSerializer, cityList)
    }

    fun fromJson(text: String): CityDto {
        return json.decodeFromString(serializer, text)
    }

    fun toJson(cityDto: CityDto): String {
        return json.encodeToString(serializer, cityDto)
    }
}
