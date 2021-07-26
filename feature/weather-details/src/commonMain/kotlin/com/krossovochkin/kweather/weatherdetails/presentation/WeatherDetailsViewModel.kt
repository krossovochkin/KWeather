package com.krossovochkin.kweather.weatherdetails.presentation

import com.krossovochkin.core.presentation.BaseViewModel
import com.krossovochkin.core.presentation.ViewModel
import com.krossovochkin.i18n.LocalizationManager
import com.krossovochkin.kweather.domain.WeatherDetails
import com.krossovochkin.kweather.weatherdetails.domain.GetCurrentCityInteractor
import com.krossovochkin.kweather.weatherdetails.domain.GetWeatherDetailsInteractor
import com.krossovochkin.kweather.weatherdetails.presentation.localization.LocalizedStringKey
import com.krossovochkin.navigation.Router
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime

interface WeatherDetailsViewModel : ViewModel<WeatherDetailsState, WeatherDetailsAction>

private const val CELSIUS_DEGREES = "°C"

class WeatherDetailsViewModelImpl(
    private val getWeatherDetailsInteractor: GetWeatherDetailsInteractor,
    private val getCurrentCityInteractor: GetCurrentCityInteractor,
    private val router: Router,
    private val localizationManager: LocalizationManager<LocalizedStringKey>
) : BaseViewModel<WeatherDetailsState,
    WeatherDetailsAction,
    WeatherDetailsActionResult>(WeatherDetailsState.Loading),
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
                cityNameText = city.name,
                todayWeatherData = with(todayWeatherData) {
                    WeatherDetailsState.Data.OneDayWeatherData(
                        currentTemperatureText = mapTemperature(currentWeatherData.temperature),
                        weatherConditionsImageUrl = currentWeatherData.conditionImageUrl,
                        weatherConditionsImageContentDescription = currentWeatherData.conditionDescription,
                        hourlyWeatherData = hourlyWeatherData.map(::mapHourlyData)
                    )
                },
                tomorrowWeatherData = with(tomorrowWeatherData) {
                    WeatherDetailsState.Data.OneDayWeatherData(
                        currentTemperatureText = null,
                        weatherConditionsImageUrl = weatherData.conditionImageUrl,
                        weatherConditionsImageContentDescription = weatherData.conditionDescription,
                        hourlyWeatherData = hourlyWeatherData.map(::mapHourlyData)
                    )
                },
                weekWeatherData = weekWeatherData.map(::mapDailyData),
                changeCityButtonText = localizationManager
                    .getString(LocalizedStringKey.WeatherDetails_ChangeCity)
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
            )
        }
    }

    private fun mapDailyData(
        weatherDetailsData: WeatherDetails.DailyWeatherData
    ): WeatherDetailsState.Data.DailyWeatherData {
        return with(weatherDetailsData) {
            WeatherDetailsState.Data.DailyWeatherData(
                dateTimeText = mapDate(weatherDetailsData.localDateTime),
                temperatureText = mapTemperature(weatherDetailsData.temperature.temperatureDay),
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

    private fun mapDate(dateTime: LocalDateTime): String {
        return "${dateTime.dayOfMonth} ${dateTime.month.name}"
    }

    override fun performAction(action: WeatherDetailsAction) {
        when (action) {
            WeatherDetailsAction.Load -> {
                scope.launch {
                    try {
                        val city = getCurrentCityInteractor.get()
                        if (city == null) {
                            onActionResult(WeatherDetailsActionResult.LoadErrorCityMissing)
                        } else {
                            val data = getWeatherDetailsInteractor.get(city)
                            onActionResult(WeatherDetailsActionResult.Loaded(weatherDetails = data))
                        }
                    } catch (e: Exception) {
                        onActionResult(WeatherDetailsActionResult.LoadErrorUnknown(e))
                    }
                }
            }
            WeatherDetailsAction.OpenSelectCityScreen -> {
                scope.launch { router.navigateTo(com.krossovochkin.navigation.RouterDestination.CityList) }
            }
        }
    }
}
