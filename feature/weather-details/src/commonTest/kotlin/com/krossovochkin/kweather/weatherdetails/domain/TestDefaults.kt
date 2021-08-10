package com.krossovochkin.kweather.weatherdetails.domain

import kotlinx.datetime.LocalDateTime

object TestDefaults {

    val DEFAULT_LOCAL_DATE_TIME = LocalDateTime(
        year = 2021,
        monthNumber = 7,
        dayOfMonth = 21,
        hour = 7,
        minute = 20
    )
    const val DEFAULT_TEMPERATURE = 20
    const val DEFAULT_PRESSURE = 1010
    const val DEFAULT_HUMIDITY = 54
    const val DEFAULT_WIND_SPEED = 3.26
    const val DEFAULT_WIND_DEGREE = 271
    const val DEFAULT_CONDITION_IMAGE_URL =
        "https://openweathermap.org/img/wn/01d@2x.png"
    const val DEFAULT_CONDITION_DESCRIPTION = "scattered clouds"
    const val DEFAULT_PRECIPITATION_VOLUME = 0.0
}
