package com.krossovochkin.kweather.feature.weatherdetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.krossovochkin.kweather.weatherdetails.presentation.WeatherDetailsAction
import com.krossovochkin.kweather.weatherdetails.presentation.WeatherDetailsState
import com.krossovochkin.kweather.weatherdetails.presentation.WeatherDetailsViewModel
import com.krossovochkin.kweather.weatherdetails.weatherDetailsModule
import org.kodein.di.DI
import org.kodein.di.instance

@Composable
fun WeatherDetailsScreen(
    parentDi: DI
) {
    val weatherDetailsViewModel = remember {
        val di = DI {
            extend(parentDi)
            import(weatherDetailsModule)
        }
        val viewModel by di.instance<WeatherDetailsViewModel>()
        viewModel
    }
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
private fun WeatherDetailsScreenImpl(
    weatherDetailsState: WeatherDetailsState?,
    onAction: (WeatherDetailsAction) -> Unit,
    onDispose: () -> Unit
) {
    DisposableEffect(null) { onDispose { onDispose() } }
    Surface(color = MaterialTheme.colors.background) {
        when (weatherDetailsState) {
            is WeatherDetailsState.Loading -> LoadingState()
            is WeatherDetailsState.UnknownError -> UnknownErrorState(state = weatherDetailsState)
            is WeatherDetailsState.Data -> DataState(
                state = weatherDetailsState,
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

private enum class WeatherTab(
    val index: Int,
    val text: String
) {
    Today(0, "Today"),
    Tomorrow(1, "Tomorrow"),
    Week(2, "Week")
    ;

    companion object {
        fun fromIndex(index: Int): WeatherTab {
            return values().first { it.index == index }
        }
    }
}

@Composable
private fun DataState(
    state: WeatherDetailsState.Data,
    onAction: (WeatherDetailsAction) -> Unit
) {
    val currentTab = remember { mutableStateOf(WeatherTab.Today) }
    val titles = WeatherTab.values().map { it.text }

    Column {
        TopAppBar(
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp
            )
        ) {
            Text(
                text = state.cityNameText,
                style = MaterialTheme.typography.h6
            )
        }

        TabRow(selectedTabIndex = currentTab.value.index) {
            titles.forEachIndexed { index, title ->
                Tab(
                    selected = currentTab.value.index == index,
                    onClick = { currentTab.value = WeatherTab.fromIndex(index) }
                ) {
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = title
                    )
                }
            }
        }

        when (currentTab.value) {
            WeatherTab.Today -> TodayDataState(weatherData = state.todayWeatherData)
            WeatherTab.Tomorrow -> TomorrowDataState(weatherData = state.tomorrowWeatherData)
            WeatherTab.Week -> WeekDataState(weekWeatherData = state.weekWeatherData)
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            onClick = { onAction(WeatherDetailsAction.OpenSelectCityScreen) }
        ) {
            Text(text = state.changeCityButtonText)
        }
    }
}

@Composable
private fun TodayDataState(
    weatherData: WeatherDetailsState.Data.OneDayWeatherData
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Row {
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
        Row {
            weatherData.temperatureCurrentText?.let { temperatureText ->
                Text(
                    text = temperatureText,
                    style = MaterialTheme.typography.h1
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Image(
                modifier = Modifier.size(144.dp),
                painter = rememberImagePainter(weatherData.weatherConditionImageUrl),
                contentDescription = weatherData.weatherConditionImageContentDescription
            )
        }
        Row {
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
        Spacer(modifier = Modifier.weight(3f))
        LazyRow {
            items(weatherData.hourlyWeatherData) { HourlyWeatherItem(it) }
        }
    }
}

@Composable
private fun TomorrowDataState(
    weatherData: WeatherDetailsState.Data.OneDayWeatherData
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Row {
            Column {
                Row {
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
                Spacer(Modifier.size(24.dp))
                Text(
                    text = weatherData.weatherConditionDescription,
                    style = MaterialTheme.typography.h4
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Image(
                modifier = Modifier.size(144.dp),
                painter = rememberImagePainter(weatherData.weatherConditionImageUrl),
                contentDescription = weatherData.weatherConditionImageContentDescription
            )
        }
        Spacer(modifier = Modifier.weight(3f))
        LazyRow {
            items(weatherData.hourlyWeatherData) {
                HourlyWeatherItem(it)
            }
        }
    }
}

@Composable
private fun WeekDataState(weekWeatherData: List<WeatherDetailsState.Data.DailyWeatherData>) {
    LazyColumn {
        items(weekWeatherData) {
            DailyWeatherItem(it)
            Divider()
        }
    }
}

@Composable
private fun HourlyWeatherItem(
    weatherData: WeatherDetailsState.Data.HourlyWeatherData
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        Text(
            text = weatherData.temperatureText,
            style = MaterialTheme.typography.body1
        )
        Image(
            modifier = Modifier.size(48.dp),
            painter = rememberImagePainter(weatherData.weatherConditionsImageUrl),
            contentDescription = weatherData.weatherConditionsImageContentDescription
        )
        Text(
            text = weatherData.dateTimeText,
            style = MaterialTheme.typography.body2
        )
    }
}

@Composable
private fun DailyWeatherItem(
    weatherData: WeatherDetailsState.Data.DailyWeatherData
) {
    Row(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.weight(1f)) {
            Text(
                text = weatherData.dateTimeText,
                style = MaterialTheme.typography.body1
            )
            Text(
                text = weatherData.weatherConditionsDescription,
                style = MaterialTheme.typography.body2
            )
        }
        Image(
            modifier = Modifier.size(72.dp),
            painter = rememberImagePainter(weatherData.weatherConditionsImageUrl),
            contentDescription = weatherData.weatherConditionsImageContentDescription
        )
        Column {
            Text(
                text = weatherData.temperatureDayText,
                style = MaterialTheme.typography.body1
            )
            Text(
                text = weatherData.temperatureNightText,
                style = MaterialTheme.typography.body2
            )
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
