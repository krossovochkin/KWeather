package com.krossovochkin.kweather.weatherdetails.presentation

import com.krossovochkin.core.presentation.BaseViewModel
import com.krossovochkin.core.presentation.ViewModel
import com.krossovochkin.i18n.LocalizationManager
import com.krossovochkin.kweather.weatherdetails.domain.GetCurrentCityIdInteractor
import com.krossovochkin.kweather.weatherdetails.domain.GetWeatherDetailsInteractor
import com.krossovochkin.kweather.weatherdetails.presentation.localization.LocalizedStringKey
import com.krossovochkin.navigation.Router
import kotlinx.coroutines.launch

interface WeatherDetailsViewModel : ViewModel<WeatherDetailsState, WeatherDetailsAction>

private const val CELSIUS_DEGREES = "°C"

class WeatherDetailsViewModelImpl(
    private val getWeatherDetailsInteractor: GetWeatherDetailsInteractor,
    private val getCurrentCityIdInteractor: GetCurrentCityIdInteractor,
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
                        WeatherDetailsState.Data(
                            cityNameText = result.weatherDetails.city.name,
                            temperatureText = "${result.weatherDetails.temperature}$CELSIUS_DEGREES",
                            weatherConditionsImageUrl = result.weatherDetails.weatherConditionsImageUrl,
                            weatherConditionsImageContentDescription = localizationManager
                                .getString(LocalizedStringKey.WeatherDetails_WeatherConditionsImageContentDescription),
                            changeCityButtonText = localizationManager
                                .getString(LocalizedStringKey.WeatherDetails_ChangeCity)
                        )
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

    override fun performAction(action: WeatherDetailsAction) {
        when (action) {
            WeatherDetailsAction.Load -> {
                scope.launch {
                    try {
                        val cityId = getCurrentCityIdInteractor.get()
                        if (cityId == null) {
                            onActionResult(WeatherDetailsActionResult.LoadErrorCityMissing)
                        } else {
                            val data = getWeatherDetailsInteractor.get(cityId = cityId)
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
