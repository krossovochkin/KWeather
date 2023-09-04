package com.krossovochkin.kweather.weathermap.presentation

import com.krossovochkin.i18n.LocalizationManager
import com.krossovochkin.kweather.navigation.RouterDestination
import com.krossovochkin.kweather.weathermap.domain.GetCurrentCityInteractor
import com.krossovochkin.kweather.weathermap.domain.GetWeatherMapDataInteractor
import com.krossovochkin.kweather.weathermap.presentation.localization.LocalizedStringKey
import com.krossovochkin.navigation.Router
import com.krossovochkin.presentation.BaseViewModel
import com.krossovochkin.presentation.ViewModel
import kotlinx.coroutines.launch

interface WeatherMapViewModel : ViewModel<WeatherMapState, WeatherMapAction>

class WeatherMapViewModelImpl(
    private val getCurrentCityInteractor: GetCurrentCityInteractor,
    private val getWeatherMapDataInteractor: GetWeatherMapDataInteractor,
    private val localizationManager: LocalizationManager<LocalizedStringKey>,
    private val router: Router<RouterDestination>,
) : BaseViewModel<
    WeatherMapState,
    WeatherMapAction,
    WeatherMapActionResult>(WeatherMapState.Loading),
    WeatherMapViewModel {

    init {
        performAction(WeatherMapAction.Load)
    }

    override suspend fun reduceState(
        state: WeatherMapState,
        result: WeatherMapActionResult
    ): WeatherMapState {
        return when (state) {
            WeatherMapState.Loading -> {
                when (result) {
                    is WeatherMapActionResult.Loaded -> {
                        WeatherMapState.Data(
                            toolbarTitle = localizationManager.getString(
                                LocalizedStringKey.WeatherMap_ToolbarTitle
                            ),
                            weatherMapData = result.weatherMapData,
                        )
                    }
                }
            }
            is WeatherMapState.Data -> reducerError(state, result)
        }
    }

    override fun performAction(action: WeatherMapAction) {
        when (action) {
            WeatherMapAction.Load -> {
                scope.launch {
                    val city = getCurrentCityInteractor.get()!!
                    val data = getWeatherMapDataInteractor.get(city)
                    onActionResult(WeatherMapActionResult.Loaded(data))
                }
            }
            WeatherMapAction.Back -> {
                scope.launch { router.navigateBack() }
            }
        }
    }
}
