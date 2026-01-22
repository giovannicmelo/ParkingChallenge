package com.parafuso.parkingchallenge.feature.parkinghistory.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parafuso.parkingchallenge.core.data.model.ApiException
import com.parafuso.parkingchallenge.feature.parkinghistory.domain.usecase.GetParkingHistoryUseCase
import com.parafuso.parkingchallenge.feature.parkinghistory.presentation.activity.ParkingHistoryActivity
import com.parafuso.parkingchallenge.feature.parkinghistory.presentation.state.ParkingHistoryViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ParkingHistoryViewModel(
    private val getParkingHistoryUseCase: GetParkingHistoryUseCase,
    private val parkingHistoryActivityArgs: ParkingHistoryActivity.Args,
) : ViewModel() {

    private val _viewState = MutableStateFlow(ParkingHistoryViewState())
    val viewState = _viewState.asStateFlow()

    init {
        getParkingHistory(parkingHistoryActivityArgs.plate)
    }

    private fun getParkingHistory(plate: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getParkingHistoryUseCase(GetParkingHistoryUseCase.Params(plate))
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
                .collect { _viewState.value = _viewState.value.copy(parkingHistory = it) }
        }
    }
}