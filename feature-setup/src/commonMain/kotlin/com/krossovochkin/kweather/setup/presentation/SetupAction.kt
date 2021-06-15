package com.krossovochkin.kweather.setup.presentation

sealed class SetupAction {

    object Load : SetupAction()
}

sealed class SetupActionResult
