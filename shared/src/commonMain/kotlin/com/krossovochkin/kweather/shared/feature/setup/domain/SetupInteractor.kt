package com.krossovochkin.kweather.shared.feature.setup.domain

interface SetupInteractor {

    suspend fun setup()
}

class SetupInteractorImpl(
    private val setupRepository: SetupRepository
) : SetupInteractor {

    override suspend fun setup() {
        setupRepository.setup()
    }
}
