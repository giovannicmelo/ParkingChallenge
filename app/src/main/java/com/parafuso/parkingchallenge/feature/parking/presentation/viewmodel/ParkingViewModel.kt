package com.parafuso.parkingchallenge.feature.parking.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parafuso.parkingchallenge.core.data.model.ApiException
import com.parafuso.parkingchallenge.core.domain.usecase.ValidatePlateUseCase
import com.parafuso.parkingchallenge.feature.parking.domain.usecase.DoParkingUseCase
import com.parafuso.parkingchallenge.feature.parking.presentation.state.ParkingViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ParkingViewModel(
    private val doParkingUseCase: DoParkingUseCase,
    private val validatePlateUseCase: ValidatePlateUseCase,
) : ViewModel() {

    private val _viewState = MutableStateFlow(ParkingViewState())
    val viewState = _viewState.asStateFlow()

    fun validatePlate(plate: String) {
        viewModelScope.launch(Dispatchers.IO) {
            validatePlateUseCase(ValidatePlateUseCase.Params(plate))
                .collect { isValid ->
                    _viewState.value = _viewState.value.copy(
                        plate = plate,
                        isValidPlate = isValid
                    )
                }
        }
    }

    fun doParking() {
        viewModelScope.launch(Dispatchers.IO) {
            doParkingUseCase(DoParkingUseCase.Params(_viewState.value.plate))
                .onStart { _viewState.value = _viewState.value.copy(isLoading = true) }
                .onCompletion { _viewState.value = _viewState.value.copy(isLoading = false) }
                .catch { throwable ->
                    _viewState.value = _viewState.value.copy(isError = true)
                    when (throwable) {
                        is ApiException -> {
                            _viewState.value =
                                _viewState.value.copy(errorMessage = throwable.message + " (" + throwable.statusCode + ")")
                        }
                        else -> {
                            _viewState.value =
                                _viewState.value.copy(errorMessage = "Ocorreu um erro inesperado")
                        }
                    }
                }
                .collect { parking ->
                    _viewState.value = _viewState.value.copy(parking = parking)
                }
        }
    }
}