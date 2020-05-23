package com.krossovochkin.kweather.shared.feature.weatherdetails.presentation

import com.krossovochkin.kweather.shared.common.image.ImageLoader
import com.krossovochkin.kweather.shared.common.presentation.BaseViewModel
import com.krossovochkin.kweather.shared.common.presentation.ViewModel
import com.krossovochkin.kweather.shared.common.router.Router
import com.krossovochkin.kweather.shared.common.router.RouterDestination
import com.krossovochkin.kweather.shared.feature.weatherdetails.domain.GetCurrentCityInteractor
import com.krossovochkin.kweather.shared.feature.weatherdetails.domain.GetWeatherDetailsInteractor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

interface WeatherDetailsViewModel : ViewModel<WeatherDetailsState, WeatherDetailsAction>

private const val CELSIUS_DEGREES = "°C"

class WeatherDetailsViewModelImpl(
    private val getWeatherDetailsInteractor: GetWeatherDetailsInteractor,
    private val getCurrentCityInteractor: GetCurrentCityInteractor,
    private val router: Router,
    private val imageLoader: ImageLoader
) : BaseViewModel<WeatherDetailsState, WeatherDetailsAction, WeatherDetailsActionResult>(
    WeatherDetailsState.Loading
), WeatherDetailsViewModel {

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
                            weatherConditionsImage = result.weatherConditionsImage
                        )
                    }
                    WeatherDetailsActionResult.LoadErrorCityMissing -> {
                        WeatherDetailsState.CityUnknownError
                    }
                    is WeatherDetailsActionResult.LoadErrorUnknown -> {
                        WeatherDetailsState.UnknownError(result.e)
                    }
                }
            }
            is WeatherDetailsState.Data -> reducerError(state, result)
            is WeatherDetailsState.UnknownError -> reducerError(state, result)
            WeatherDetailsState.CityUnknownError -> reducerError(state, result)
        }
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
                            val data = getWeatherDetailsInteractor.get(city = city)
                            onActionResult(
                                WeatherDetailsActionResult.Loaded(
                                    weatherDetails = data,
                                    weatherConditionsImage = imageLoader.load(data.weatherConditionsImageUrl)
                                )
                            )
                        }
                    } catch (e: Exception) {
                        onActionResult(WeatherDetailsActionResult.LoadErrorUnknown(e))
                    }
                }
            }
            WeatherDetailsAction.OpenSelectCityScreen -> {
                router.navigateTo(RouterDestination.CityList)
            }
        }
    }
}