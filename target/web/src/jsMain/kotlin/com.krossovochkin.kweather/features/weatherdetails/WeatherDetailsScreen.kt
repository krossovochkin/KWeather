@file:OptIn(ExperimentalComposeWebWidgetsApi::class)

package com.krossovochkin.kweather.features.weatherdetails

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import com.krossovochkin.kweather.weatherdetails.presentation.WeatherDetailsAction
import com.krossovochkin.kweather.weatherdetails.presentation.WeatherDetailsState
import com.krossovochkin.kweather.weatherdetails.presentation.WeatherDetailsViewModel
import com.krossovochkin.kweather.weatherdetails.weatherDetailsModule
import org.jetbrains.compose.common.foundation.layout.Box
import org.jetbrains.compose.common.foundation.layout.Column
import org.jetbrains.compose.common.foundation.layout.Row
import org.jetbrains.compose.common.foundation.layout.fillMaxHeight
import org.jetbrains.compose.common.foundation.layout.fillMaxWidth
import org.jetbrains.compose.common.ui.ExperimentalComposeWebWidgetsApi
import org.jetbrains.compose.common.ui.Modifier
import org.jetbrains.compose.common.ui.padding
import org.jetbrains.compose.common.ui.unit.dp
import org.jetbrains.compose.web.dom.Button
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
    Box(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(1f)
    ) {
        Text("Loading...")
    }
}

@Composable
private fun UnknownErrorState(state: WeatherDetailsState.UnknownError) {
    Text("${state.error}")
}

@Composable
private fun WeatherDetailsScreenImpl(
    weatherDetailsState: WeatherDetailsState?,
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
    Column {
        Row(modifier = Modifier.padding(16.dp)) {
            Text(weatherData.temperatureDayText)
            Text(weatherData.temperatureNightText)
        }
        Row(modifier = Modifier.padding(16.dp)) {
            weatherData.temperatureCurrentText?.let { temperatureText ->
                Text(temperatureText)
            }
            Img(weatherData.weatherConditionImageUrl)
        }
        Row(modifier = Modifier.padding(16.dp)) {
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
}

@Composable
private fun CityUnknownErrorState(
    state: WeatherDetailsState.CityUnknownError,
    onAction: (WeatherDetailsAction) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(16.dp),
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
}
