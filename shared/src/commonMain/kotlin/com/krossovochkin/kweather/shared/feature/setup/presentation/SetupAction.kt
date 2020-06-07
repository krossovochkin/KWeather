package com.krossovochkin.kweather.shared.feature.setup.presentation

sealed class SetupAction {

    object Load : SetupAction()
}

sealed class SetupActionResult
