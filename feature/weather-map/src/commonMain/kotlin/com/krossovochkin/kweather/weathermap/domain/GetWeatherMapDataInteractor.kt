package com.krossovochkin.kweather.weathermap.domain

import com.krossovochkin.kweather.domain.City
import kotlin.math.PI
import kotlin.math.asinh
import kotlin.math.atan
import kotlin.math.floor
import kotlin.math.pow
import kotlin.math.sinh
import kotlin.math.tan

interface GetWeatherMapDataInteractor {

    fun get(city: City): WeatherMapData
}

private const val ZOOM = 8

internal class GetWeatherMapDataInteractorImpl(
    private val mapboxApiKey: String,
    private val openWeatherMapApiKey: String,
) : GetWeatherMapDataInteractor {

    override fun get(city: City): WeatherMapData {
        val (x, y) = getXYTile(
            lat = city.location.latitude,
            lon = city.location.longitude,
            zoom = ZOOM
        )
        val tileBounds = getTileBounds(x, y, ZOOM)

        val startX = x - 1
        val endX = x + 1
        val startY = y - 1
        val endY = y + 1

        return WeatherMapData(
            city = city,
            tileBounds = tileBounds,
            zoom = ZOOM,
            mapTileUrls = (startY..endY).map { currentY ->
                (startX..endX).map { currentX ->
                    getMapUrl(currentX, currentY, ZOOM)
                }
            },
            precipitationTileUrls = (startY..endY).map { currentY ->
                (startX..endX).map { currentX ->
                    getPrecipitationUrl(currentX, currentY, ZOOM)
                }
            }
        )
    }

    private fun getMapUrl(x: Int, y: Int, z: Int): String {
        return "https://api.mapbox.com/styles/v1/mapbox/light-v10/tiles/256/$z/$x/$y?access_token=$mapboxApiKey"
    }

    private fun getPrecipitationUrl(x: Int, y: Int, z: Int): String {
        return "https://tile.openweathermap.org/map/precipitation_new/$z/$x/$y.png?appid=$openWeatherMapApiKey"
    }

    private fun getXYTile(lat: Double, lon: Double, zoom: Int): Pair<Int, Int> {
        val latRad = lat * PI / 180
        var xtile = floor((lon + 180) / 360 * (1 shl zoom)).toInt()
        var ytile = floor((1.0 - asinh(tan(latRad)) / PI) / 2 * (1 shl zoom)).toInt()

        if (xtile < 0) {
            xtile = 0
        }
        if (xtile >= (1 shl zoom)) {
            xtile = (1 shl zoom) - 1
        }
        if (ytile < 0) {
            ytile = 0
        }
        if (ytile >= (1 shl zoom)) {
            ytile = (1 shl zoom) - 1
        }

        return Pair(xtile, ytile)
    }

    private fun getTileBounds(x: Int, y: Int, zoom: Int): TileBounds {
        return TileBounds(
            north = tile2lat(y, zoom),
            south = tile2lat(y + 1, zoom),
            east = tile2lon(x, zoom),
            west = tile2lon(x + 1, zoom)
        )
    }

    private fun tile2lon(x: Int, z: Int): Double {
        return x / 2.0.pow(z.toDouble()) * 360.0 - 180
    }

    private fun tile2lat(y: Int, z: Int): Double {
        val n = PI - 2.0 * PI * y / 2.0.pow(z.toDouble())
        return atan(sinh(n)) * 180 / PI
    }
}
