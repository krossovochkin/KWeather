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
import com.krossovochkin.kweather.shared.feature.setup.presentation.SetupAction
import com.krossovochkin.kweather.shared.feature.setup.presentation.SetupState
import com.krossovochkin.kweather.shared.feature.setup.presentation.SetupViewModel
import com.krossovochkin.kweather.shared.feature.setup.setupModule
import org.kodein.di.DI
import org.kodein.di.instance

@Composable
fun SetupScreen(
    parentDi: DI
) {
    val setupViewModel = state {
        val di = DI {
            extend(parentDi)
            import(setupModule)
        }
        val viewModel by di.instance<SetupViewModel>()
        viewModel
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