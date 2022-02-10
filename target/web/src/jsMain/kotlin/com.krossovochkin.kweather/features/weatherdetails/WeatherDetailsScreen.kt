package com.krossovochkin.kweather.features.weatherdetails

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import com.krossovochkin.kweather.weatherdetails.presentation.WeatherDetailsAction
import com.krossovochkin.kweather.weatherdetails.presentation.WeatherDetailsState
import com.krossovochkin.kweather.weatherdetails.presentation.WeatherDetailsViewModel
import com.krossovochkin.kweather.weatherdetails.weatherDetailsModule
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.Text
import org.kodein.di.DI
import org.kodein.di.instance

@Composable
fun WeatherDetailsScreen(parentDi: DI) {
    val di = remember {
        DI {
            extend(parentDi)
            import(weatherDetailsModule)
        }
    }

    val weatherDetailsViewModel: WeatherDetailsViewModel by di.instance()

    val weatherDetailsState = weatherDetailsViewModel
        .observeState()
        .collectAsState(WeatherDetailsState.Loading)
        .value

    WeatherDetailsScreenImpl(
        weatherDetailsState,
        weatherDetailsViewModel::performAction,
        weatherDetailsViewModel::dispose
    )
}

@Composable
private fun LoadingState() {
    Text("Loading...")
}

@Composable
private fun UnknownErrorState(state: WeatherDetailsState.UnknownError) {
    Text("${state.error}")
}

@Composable
private fun WeatherDetailsScreenImpl(
    weatherDetailsState: WeatherDetailsState,
    onAction: (WeatherDetailsAction) -> Unit,
    onDispose: () -> Unit
) {
    DisposableEffect(null) { onDispose { onDispose() } }
    when (weatherDetailsState) {
        is WeatherDetailsState.Loading -> LoadingState()
        is WeatherDetailsState.UnknownError -> UnknownErrorState(state = weatherDetailsState)
        is WeatherDetailsState.Data -> TodayDataState(
            changeCityText = weatherDetailsState.changeCityText,
            weatherData = weatherDetailsState.todayWeatherData,
            onAction = { onAction(it) }
        )
        is WeatherDetailsState.CityUnknownError -> CityUnknownErrorState(
            state = weatherDetailsState,
            onAction = { onAction(it) }
        )
    }
}

@Composable
private fun TodayDataState(
    changeCityText: String,
    weatherData: WeatherDetailsState.Data.OneDayWeatherData,
    onAction: (WeatherDetailsAction) -> Unit
) {
    Div {
        Text(weatherData.temperatureDayText)
        Text(weatherData.temperatureNightText)
    }
    Div {
        weatherData.temperatureCurrentText?.let { temperatureText ->
            Text(temperatureText)
        }
        Img(weatherData.weatherConditionImageUrl)
    }
    Div {
        weatherData.temperatureFeelsLikeText?.let { temperatureText ->
            Text(temperatureText)
        }
        Text(weatherData.weatherConditionDescription)
    }
    Button(
        attrs = {
            onClick { onAction(WeatherDetailsAction.OpenSelectCityScreen) }
        }
    ) {
        Text(changeCityText)
    }
}

@Composable
private fun CityUnknownErrorState(
    state: WeatherDetailsState.CityUnknownError,
    onAction: (WeatherDetailsAction) -> Unit
) {
    Text(state.cityMissingMessageText)
    Button(
        attrs = {
            onClick { onAction(WeatherDetailsAction.OpenSelectCityScreen) }
        }
    ) {
        Text(state.selectCityButtonText)
    }
}
