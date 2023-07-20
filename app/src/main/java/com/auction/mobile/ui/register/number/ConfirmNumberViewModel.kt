package com.auction.mobile.ui.register.number

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auction.mobile.domain.models.BaseResponse
import com.auction.mobile.domain.repository.ConfirmNumberRepository
import com.auction.mobile.tools.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfirmNumberViewModel @Inject constructor(
    private val repo: ConfirmNumberRepository
) : ViewModel() {

    private val _dataFlow = MutableLiveData<Unit>()
    val dataFlow: LiveData<Unit> = _dataFlow

    private val _failFlow = com.auction.mobile.tools.SingleLiveEvent<String>()
    val failFlow: LiveData<String> = _failFlow
    private val _progressFlow = MutableLiveData<Boolean>()
    val progressFlow: LiveData<Boolean> = _progressFlow

    fun sendNumber(number: String) {
        viewModelScope.launch {
            _progressFlow.value = true
            val result = repo.sendNumber(number)
            when (result) {
                is BaseResponse.Success -> {
                    _dataFlow.value = Unit
                }
                is BaseResponse.Error -> {
                    _failFlow.value = result.message
                }
            }
            _progressFlow.value = false
        }
    }

    fun sendNumberForPassRecovery(info: String) {
        viewModelScope.launch {
            _progressFlow.value = true
            val result = repo.confirmPass(info)
            when (result) {
                is BaseResponse.Success -> {
                    _dataFlow.value = Unit
                }
                is BaseResponse.Error -> {
                    _failFlow.value = result.message
                }
            }
            _progressFlow.value = false
        }
    }
}