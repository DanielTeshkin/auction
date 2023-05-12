package com.example.auctionapp.ui.register.number

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auctionapp.domain.models.BaseResponse
import com.example.auctionapp.domain.models.ConfirmCodeResponse
import com.example.auctionapp.domain.models.ConfirmPhoneModel
import com.example.auctionapp.domain.repository.ConfirmNumberRepository
import com.example.auctionapp.tools.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfirmNumberViewModel @Inject constructor(
    private val repo: ConfirmNumberRepository
) : ViewModel() {

    private val _dataFlow = MutableLiveData<ConfirmCodeResponse>()
    val dataFlow: LiveData<ConfirmCodeResponse> = _dataFlow

    private val _failFlow = SingleLiveEvent<String>()
    val failFlow: LiveData<String> = _failFlow
    private val _progressFlow = MutableLiveData<Boolean>()
    val progressFlow: LiveData<Boolean> = _progressFlow

    fun sendNumber(number: String) {
        viewModelScope.launch {
            _progressFlow.value = true
            val result = repo.sendNumber(number)
            when (result) {
                is BaseResponse.Success -> {
                    _dataFlow.value = result.data as ConfirmCodeResponse
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
                    _dataFlow.value = result.data as ConfirmCodeResponse
                }
                is BaseResponse.Error -> {
                    _failFlow.value = result.message
                }
            }
            _progressFlow.value = false
        }
    }
}