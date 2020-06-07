package com.krossovochkin.kweather.feature.setup

import androidx.compose.Composable
import androidx.compose.collectAsState
import androidx.compose.state
import androidx.ui.core.Modifier
import androidx.ui.foundation.Box
import androidx.ui.foundation.ContentGravity
import androidx.ui.layout.fillMaxSize
import androidx.ui.material.CircularProgressIndicator
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Surface
import com.krossovochkin.kweather.AppModule
import com.krossovochkin.kweather.shared.feature.setup.SetupModule
import com.krossovochkin.kweather.shared.feature.setup.presentation.SetupAction
import com.krossovochkin.kweather.shared.feature.setup.presentation.SetupState

@Composable
fun SetupScreen(
    appModule: AppModule
) {
    val setupViewModel = state {
        SetupModule(
            router = appModule.router,
            localizationManager = appModule.localizationManager,
            storageModule = appModule.storageModule
        ).viewModel
    }.value
    val setupState = setupViewModel
        .observeState()
        .collectAsState()
        .value
    SetupScreenImpl(
        setupState,
        setupViewModel::performAction,
        setupViewModel::dispose
    )
}

@Composable
private fun SetupScreenImpl(
    setupState: SetupState?,
    onAction: (SetupAction) -> Unit,
    onDispose: () -> Unit
) {
    androidx.compose.onDispose(callback = { onDispose() })
    Surface(color = MaterialTheme.colors.background) {
        when (setupState) {
            is SetupState.Loading -> LoadingState()
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