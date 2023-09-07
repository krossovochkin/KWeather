package com.krossovochkin.kweather.weatherdetails.presentation

import com.krossovochkin.i18n.LocalizationManager
import com.krossovochkin.kweather.domain.CityId
import com.krossovochkin.kweather.domain.CityLocation
import com.krossovochkin.kweather.domain.WeatherDetails
import com.krossovochkin.kweather.navigation.RouterDestination
import com.krossovochkin.kweather.weatherdetails.domain.GetCurrentCityInteractor
import com.krossovochkin.kweather.weatherdetails.domain.GetWeatherDetailsInteractor
import com.krossovochkin.kweather.weatherdetails.presentation.localization.LocalizedStringKey
import com.krossovochkin.location.LocationProvider
import com.krossovochkin.navigation.Router
import com.krossovochkin.presentation.BaseViewModel
import com.krossovochkin.presentation.ViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime

interface WeatherDetailsViewModel : ViewModel<WeatherDetailsState, WeatherDetailsAction>

private const val CELSIUS_DEGREES = "°C"

class WeatherDetailsViewModelImpl(
    private val getWeatherDetailsInteractor: GetWeatherDetailsInteractor,
    private val getCurrentCityInteractor: GetCurrentCityInteractor,
    private val router: Router<RouterDestination>,
    private val localizationManager: LocalizationManager<LocalizedStringKey>,
    private val locationProvider: LocationProvider,
    defaultDispatcher: CoroutineDispatcher,
) : BaseViewModel<
    WeatherDetailsState,
    WeatherDetailsAction,
    WeatherDetailsActionResult
    >(WeatherDetailsState.Loading, defaultDispatcher),
    WeatherDetailsViewModel {

    init {
        performAction(WeatherDetailsAction.Load)
    }

    override suspend fun reduceState(
        state: WeatherDetailsState,
        result: WeatherDetailsActionResult
    ): WeatherDetailsState {
        return when (state) {
            WeatherDetailsState.Loading -> {
                when (result) {
                    is WeatherDetailsActionResult.Loaded -> {
                        map(result.weatherDetails)
                    }
                    WeatherDetailsActionResult.LoadErrorCityMissing -> {
                        WeatherDetailsState.CityUnknownError(
                            cityMissingMessageText = localizationManager.getString(
                                LocalizedStringKey.WeatherDetails_CityMissingMessage
                            ),
                            selectCityButtonText = localizationManager
                                .getString(LocalizedStringKey.WeatherDetails_SelectCity)
                        )
                    }
                    is WeatherDetailsActionResult.LoadErrorUnknown -> {
                        WeatherDetailsState.UnknownError(result.e)
                    }
                }
            }
            is WeatherDetailsState.Data -> reducerError(state, result)
            is WeatherDetailsState.UnknownError -> reducerError(state, result)
            is WeatherDetailsState.CityUnknownError -> reducerError(state, result)
        }
    }

    private fun map(weatherDetails: WeatherDetails): WeatherDetailsState.Data {
        return with(weatherDetails) {
            WeatherDetailsState.Data(
                changeCityText = localizationManager.getString(
                    LocalizedStringKey.WeatherDetails_ChangeCityText
                ),
                cityNameText = city.name,
                todayWeatherData = mapOneDayWeatherData(todayWeatherData),
                tomorrowWeatherData = mapOneDayWeatherData(tomorrowWeatherData),
                weekWeatherData = weekWeatherData.map(::mapDailyData),
            )
        }
    }

    private fun mapOneDayWeatherData(
        oneDayWeatherData: WeatherDetails.OneDayWeatherData
    ): WeatherDetailsState.Data.OneDayWeatherData {
        return with(oneDayWeatherData) {
            WeatherDetailsState.Data.OneDayWeatherData(
                temperatureDayText = localizationManager.getString(
                    LocalizedStringKey.WeatherDetails_TemperatureDay,
                    mapTemperature(weatherData.temperature.temperatureDay)
                ),
                temperatureNightText = localizationManager.getString(
                    LocalizedStringKey.WeatherDetails_TemperatureNight,
                    mapTemperature(weatherData.temperature.temperatureNight)
                ),
                temperatureCurrentText = weatherData.temperature
                    .temperatureCurrent?.let(::mapTemperature),
                temperatureFeelsLikeText = weatherData.temperature
                    .temperatureFeelsLike?.let { temperature ->
                        localizationManager.getString(
                            LocalizedStringKey.WeatherDetails_TemperatureFeelsLike,
                            mapTemperature(temperature)
                        )
                    },
                weatherConditionImageUrl = weatherData.conditionImageUrl,
                weatherConditionImageContentDescription = weatherData.conditionDescription,
                weatherConditionDescription = weatherData.conditionDescription,
                hourlyWeatherData = hourlyWeatherData.map(::mapHourlyData)
            )
        }
    }

    private fun mapHourlyData(
        weatherDetailsData: WeatherDetails.HourlyWeatherData
    ): WeatherDetailsState.Data.HourlyWeatherData {
        return with(weatherDetailsData) {
            WeatherDetailsState.Data.HourlyWeatherData(
                dateTimeText = mapTime(localDateTime),
                temperatureText = mapTemperature(temperature),
                weatherConditionsImageUrl = conditionImageUrl,
                weatherConditionsImageContentDescription = localizationManager
                    .getString(
                        LocalizedStringKey.WeatherDetails_WeatherConditionsImageContentDescription
                    ),
                precipitationVolumeText = "$precipitationVolume"
            )
        }
    }

    private fun mapDailyData(
        weatherDetailsData: WeatherDetails.DailyWeatherData
    ): WeatherDetailsState.Data.DailyWeatherData {
        return with(weatherDetailsData) {
            WeatherDetailsState.Data.DailyWeatherData(
                dateTimeText = mapWeekdayWithDate(weatherDetailsData.localDateTime),
                temperatureDayText = mapTemperature(weatherDetailsData.temperature.temperatureDay),
                temperatureNightText = mapTemperature(weatherDetailsData.temperature.temperatureNight),
                weatherConditionsImageUrl = conditionImageUrl,
                weatherConditionsImageContentDescription = localizationManager
                    .getString(
                        LocalizedStringKey.WeatherDetails_WeatherConditionsImageContentDescription
                    ),
                weatherConditionsDescription = conditionDescription
            )
        }
    }

    private fun mapTemperature(temperature: Int): String {
        return "${temperature}$CELSIUS_DEGREES"
    }

    private fun mapTime(dateTime: LocalDateTime): String {
        val hourText = "${dateTime.hour}".padStart(2, '0')
        val minuteText = "${dateTime.minute}".padStart(2, '0')
        return "$hourText:$minuteText"
    }

    private fun mapWeekdayWithDate(dateTime: LocalDateTime): String {
        val weekday = dateTime.dayOfWeek.name.lowercase()
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
        val month = dateTime.month.name.lowercase()
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
        return "$weekday, ${dateTime.dayOfMonth} $month"
    }

    override fun performAction(action: WeatherDetailsAction) {
        when (action) {
            WeatherDetailsAction.Load -> {
                scope.launch {
                    try {
                        var city = getCurrentCityInteractor.get()
                        if (city == null) {
                            onActionResult(WeatherDetailsActionResult.LoadErrorCityMissing)
                        } else {
                            if (city.id == CityId.currentLocation) {
                                city = city.copy(
                                    location = locationProvider.getLastLocation()
                                        .let { location ->
                                            CityLocation(
                                                latitude = location.latitude,
                                                longitude = location.longitude,
                                            )
                                        }
                                )
                            }
                            val data = getWeatherDetailsInteractor.get(city)
                            onActionResult(WeatherDetailsActionResult.Loaded(weatherDetails = data))
                        }
                    } catch (e: Exception) {
                        onActionResult(WeatherDetailsActionResult.LoadErrorUnknown(e))
                    }
                }
            }
            WeatherDetailsAction.OpenSelectCityScreen -> {
                scope.launch { router.navigateTo(RouterDestination.CityList) }
            }
            WeatherDetailsAction.OpenWeatherMapScreen -> {
                scope.launch { router.navigateTo(RouterDestination.WeatherMap) }
            }
        }
    }
}
