package com.krossovochkin.kweather.features.weatherdetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.krossovochkin.imageloader.ImageLoader
import com.krossovochkin.kweather.weatherdetails.presentation.WeatherDetailsAction
import com.krossovochkin.kweather.weatherdetails.presentation.WeatherDetailsState
import com.krossovochkin.kweather.weatherdetails.presentation.WeatherDetailsViewModel
import com.krossovochkin.kweather.weatherdetails.weatherDetailsModule
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
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun UnknownErrorState(state: WeatherDetailsState.UnknownError) {
    Text(text = "${state.error}")
}

@Composable
private fun WeatherDetailsScreenImpl(
    weatherDetailsState: WeatherDetailsState,
    onAction: (WeatherDetailsAction) -> Unit,
    onDispose: () -> Unit
) {
    DisposableEffect(null) { onDispose { onDispose() } }
    Surface(color = MaterialTheme.colors.background) {
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
}

@Composable
private fun TodayDataState(
    changeCityText: String,
    weatherData: WeatherDetailsState.Data.OneDayWeatherData,
    onAction: (WeatherDetailsAction) -> Unit
) {
    Column {
        Row(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(
                text = weatherData.temperatureDayText,
                style = MaterialTheme.typography.subtitle1
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = weatherData.temperatureNightText,
                style = MaterialTheme.typography.subtitle1
            )
        }
        Row(modifier = Modifier.padding(horizontal = 16.dp)) {
            weatherData.temperatureCurrentText?.let { temperatureText ->
                Text(
                    text = temperatureText,
                    style = MaterialTheme.typography.h1
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            ImageLoader.rememberImagePainter(weatherData.weatherConditionImageUrl)?.let {
                Image(
                    painter = it,
                    modifier = Modifier.size(160.dp),
                    contentDescription = weatherData.weatherConditionImageContentDescription
                )
            }
        }
        Row(modifier = Modifier.padding(horizontal = 16.dp)) {
            weatherData.temperatureFeelsLikeText?.let { temperatureText ->
                Text(
                    text = temperatureText,
                    style = MaterialTheme.typography.subtitle1
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = weatherData.weatherConditionDescription,
                style = MaterialTheme.typography.subtitle1
            )
        }
        Button(
            modifier = Modifier.padding(16.dp),
            onClick = { onAction(WeatherDetailsAction.OpenSelectCityScreen) }
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
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.weight(1f, fill = true),
            text = state.cityMissingMessageText
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onAction(WeatherDetailsAction.OpenSelectCityScreen) }
        ) {
            Text(text = state.selectCityButtonText)
        }
    }
}
