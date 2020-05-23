package com.krossovochkin.kweather.feature.citylist

import androidx.compose.Composable
import androidx.compose.collectAsState
import androidx.compose.state
import androidx.ui.core.Modifier
import androidx.ui.foundation.*
import androidx.ui.layout.Column
import androidx.ui.layout.fillMaxSize
import androidx.ui.layout.fillMaxWidth
import androidx.ui.layout.padding
import androidx.ui.material.*
import androidx.ui.unit.Dp
import com.krossovochkin.kweather.AppModule
import com.krossovochkin.kweather.shared.feature.citylist.CityListModule
import com.krossovochkin.kweather.shared.feature.citylist.presentation.CityListAction
import com.krossovochkin.kweather.shared.feature.citylist.presentation.CityListState
import com.krossovochkin.kweather.shared.feature.citylist.presentation.CityListViewModel

@Composable
fun CityListScreen(
    appModule: AppModule
) {
    val cityListViewModel: CityListViewModel = state {
        CityListModule(
            router = appModule.router,
            storageModule = appModule.storageModule,
            localizationManager = appModule.localizationManager
        ).viewModel
    }.value
    val cityListState = cityListViewModel
        .observeState()
        .collectAsState()
        .value
    CityListScreenImpl(
        cityListState,
        cityListViewModel::performAction,
        cityListViewModel::dispose
    )
}

@Composable
private fun CityListScreenImpl(
    cityListState: CityListState?,
    onAction: (CityListAction) -> Unit,
    onDispose: () -> Unit
) {
    androidx.compose.onDispose(callback = { onDispose() })
    Surface(color = MaterialTheme.colors.background) {
        when (cityListState) {
            is CityListState.Loading -> LoadingState()
            is CityListState.Data -> DataState(
                state = cityListState,
                onAction = { onAction(it) }
            )
            is CityListState.Error -> ErrorState(state = cityListState)
        }
    }
}

@Composable
private fun LoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        gravity = ContentGravity.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun DataState(
    state: CityListState.Data,
    onAction: (CityListAction) -> Unit
) {
    Column(
        modifier = Modifier.padding(Dp(16f))
    ) {
        FilledTextField(
            modifier = Modifier.padding(bottom = Dp(16f)).fillMaxWidth(),
            value = state.queryText,
            label = { Text(state.cityNameHintText) },
            onValueChange = {
                onAction(CityListAction.ChangeCityNameQuery(it))
            }
        )
        AdapterList(data = state.cityList) { city ->
            Clickable(onClick = { onAction(CityListAction.SelectCity(city)) }) {
                Text(
                    modifier = Modifier.padding(top = Dp(16f), bottom = Dp(16f)),
                    text = city.name,
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}

@Composable
private fun ErrorState(
    state: CityListState.Error
) {
    Text(text = "${state.error}")
}
