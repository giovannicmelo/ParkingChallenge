package com.parafuso.parkingchallenge.core.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface ValidatePlateUseCase {
    operator fun invoke(params: Params): Flow<Boolean>

    data class Params(val plate: String)
}

class ValidatePlateUseCaseImpl : ValidatePlateUseCase {

    override fun invoke(params: ValidatePlateUseCase.Params): Flow<Boolean> {
        return flow {
            val plateRegex = "[A-Z]{3}-\\d{4}".toRegex()
            emit(params.plate.matches(plateRegex))
        }
    }
}