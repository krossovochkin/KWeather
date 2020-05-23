package com.krossovochkin.kweather.feature.citylist

import androidx.compose.Composable
import androidx.compose.collectAsState
import androidx.compose.state
import androidx.ui.core.Modifier
import androidx.ui.foundation.*
import androidx.ui.layout.Column
import androidx.ui.layout.fillMaxSize
import androidx.ui.layout.padding
import androidx.ui.material.CircularProgressIndicator
import androidx.ui.material.FilledTextField
import androidx.ui.material.Snackbar
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
            storageModule = appModule.storageModule
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
    return when (cityListState) {
        is CityListState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                gravity = ContentGravity.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is CityListState.Data -> {
            Column(
                modifier = Modifier.padding(Dp(16f))
            ) {
                FilledTextField(
                    value = cityListState.queryText,
                    label = { Text("City name") },
                    onValueChange = {
                        onAction(CityListAction.ChangeCityNameQuery(it))
                    }
                )
                AdapterList(data = cityListState.cityList) { city ->
                    Clickable(onClick = { onAction(CityListAction.SelectCity(city)) }) {
                        Text(text = city.name)
                    }
                }
            }
        }
        is CityListState.Error -> {
            Text(text = "Error: ${cityListState.error}")
        }
        else -> {
        }
    }
}