package com.krossovochkin.kweather.weatherdetails.data

import com.krossovochkin.kweather.network.dto.WeatherDetailsDto
import com.krossovochkin.kweather.domain.City
import com.krossovochkin.kweather.domain.WeatherDetails
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

internal interface WeatherDetailsMapper {

    fun map(city: City, dto: WeatherDetailsDto): WeatherDetails
}

private const val HOUR_DAY_START = 6
private const val WEATHER_CONDITIONS_IMAGE_URL = "https://openweathermap.org/img/wn/"

internal class WeatherDetailsMapperImpl : WeatherDetailsMapper {

    override fun map(city: City, dto: WeatherDetailsDto): WeatherDetails {
        val hourlyWeatherData = splitHourlyWeatherData(
            dto.hourlyWeatherData.map(::mapHourlyWeatherData)
        )

        return WeatherDetails(
            city = city,
            todayWeatherData = mapTodayWeatherData(
                dto = dto,
                dailyWeatherData = dto.dailyWeatherData[0],
                hourlyWeatherData = hourlyWeatherData[0]
            ),
            tomorrowWeatherData = mapTomorrowWeatherData(dto, hourlyWeatherData[1]),
            weekWeatherData = mapWeekWeatherData(dto),
        )
    }

    private fun splitHourlyWeatherData(
        hourlyWeatherData: List<WeatherDetails.HourlyWeatherData>
    ): List<List<WeatherDetails.HourlyWeatherData>> {
        val result = mutableListOf<List<WeatherDetails.HourlyWeatherData>>()

        var current = mutableListOf<WeatherDetails.HourlyWeatherData>()

        val offset = if (hourlyWeatherData.first().localDateTime.hour == HOUR_DAY_START) {
            current += hourlyWeatherData.first()
            1
        } else {
            0
        }

        for (i in offset until hourlyWeatherData.size) {
            val data = hourlyWeatherData[i]

            if (data.localDateTime.hour == HOUR_DAY_START) {
                result += current
                current = mutableListOf()
            }

            current += data
        }

        return result
    }

    private fun mapTodayWeatherData(
        dto: WeatherDetailsDto,
        dailyWeatherData: WeatherDetailsDto.DailyWeatherDataDto,
        hourlyWeatherData: List<WeatherDetails.HourlyWeatherData>
    ): WeatherDetails.OneDayWeatherData {
        return WeatherDetails.OneDayWeatherData(
            weatherData = with(dto.currentWeatherData) {
                WeatherDetails.DailyWeatherData(
                    localDateTime = mapTime(timestamp),
                    temperature = WeatherDetails.DailyWeatherData.TemperatureData(
                        temperatureDay = dailyWeatherData.temperature.temperatureDay.toInt(),
                        temperatureNight = dailyWeatherData.temperature.temperatureNight.toInt(),
                        temperatureCurrent = temperature.toInt(),
                        temperatureFeelsLike = temperatureFeelsLike.toInt(),
                    ),
                    pressure = pressure,
                    humidity = humidity,
                    windSpeed = windSpeed,
                    windDegree = windDegree,
                    conditionImageUrl = mapConditionImageUrl(conditions.first().icon),
                    conditionDescription = mapConditionDescription(conditions.first().description)
                )
            },
            hourlyWeatherData = hourlyWeatherData
        )
    }

    private fun mapTomorrowWeatherData(
        dto: WeatherDetailsDto,
        hourlyWeatherData: List<WeatherDetails.HourlyWeatherData>
    ): WeatherDetails.OneDayWeatherData {
        return WeatherDetails.OneDayWeatherData(
            weatherData = mapDailyWeatherData(dto.dailyWeatherData[1]),
            hourlyWeatherData = hourlyWeatherData
        )
    }

    private fun mapWeekWeatherData(dto: WeatherDetailsDto): List<WeatherDetails.DailyWeatherData> {
        return dto.dailyWeatherData.map(::mapDailyWeatherData)
    }

    private fun mapHourlyWeatherData(
        weatherDataDto: WeatherDetailsDto.HourlyWeatherDataDto
    ): WeatherDetails.HourlyWeatherData {
        return with(weatherDataDto) {
            WeatherDetails.HourlyWeatherData(
                localDateTime = mapTime(timestamp),
                temperature = temperature.toInt(),
                temperatureFeelsLike = temperatureFeelsLike.toInt(),
                pressure = pressure,
                humidity = humidity,
                windSpeed = windSpeed,
                windDegree = windDegree,
                conditionImageUrl = mapConditionImageUrl(
                    icon = conditions.first().icon
                ),
                conditionDescription = mapConditionDescription(conditions.first().description),
                precipitationVolume = rainData?.volume ?: snowData?.volume ?: 0.0
            )
        }
    }

    private fun mapDailyWeatherData(
        weatherDataDto: WeatherDetailsDto.DailyWeatherDataDto
    ): WeatherDetails.DailyWeatherData {
        return with(weatherDataDto) {
            WeatherDetails.DailyWeatherData(
                localDateTime = mapTime(timestamp),
                temperature = mapDailyTemperatureData(
                    temperatureDataDto = temperature,
                    temperatureCurrent = null,
                    temperatureFeelsLike = null
                ),
                pressure = pressure,
                humidity = humidity,
                windSpeed = windSpeed,
                windDegree = windDegree,
                conditionImageUrl = mapConditionImageUrl(
                    icon = conditions.first().icon
                ),
                conditionDescription = mapConditionDescription(conditions.first().description)
            )
        }
    }

    private fun mapDailyTemperatureData(
        temperatureDataDto: WeatherDetailsDto.DailyWeatherDataDto.TemperatureDataDto,
        temperatureCurrent: Double?,
        temperatureFeelsLike: Double?
    ): WeatherDetails.DailyWeatherData.TemperatureData {
        return with(temperatureDataDto) {
            WeatherDetails.DailyWeatherData.TemperatureData(
                temperatureDay = temperatureDay.toInt(),
                temperatureNight = temperatureNight.toInt(),
                temperatureCurrent = temperatureCurrent?.toInt(),
                temperatureFeelsLike = temperatureFeelsLike?.toInt(),
            )
        }
    }

    private fun mapConditionImageUrl(icon: String): String {
        return "$WEATHER_CONDITIONS_IMAGE_URL$icon@4x.png"
    }

    private fun mapConditionDescription(description: String): String {
        return description.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
    }

    private fun mapTime(timestamp: Long): LocalDateTime {
        return Instant.fromEpochSeconds(timestamp).toLocalDateTime(TimeZone.currentSystemDefault())
    }
}
