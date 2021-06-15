package com.krossovochkin.kweather.setup.domain

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
