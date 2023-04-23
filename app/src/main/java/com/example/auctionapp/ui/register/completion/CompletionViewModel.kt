package com.example.auctionapp.ui.register.completion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auctionapp.domain.models.BaseResponse
import com.example.auctionapp.domain.models.CitiesModel
import com.example.auctionapp.domain.repository.CompletionRepository
import com.example.auctionapp.tools.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompletionViewModel @Inject constructor(
private val repo: CompletionRepository
): ViewModel() {

    private val _successLive = MutableLiveData<Unit>()
    val successLive: LiveData<Unit> get() = _successLive

    private val _failLive = SingleLiveEvent<String>()
    val failLive: LiveData<String> get() = _failLive

    private val _progressLive = MutableLiveData<Boolean>()
    val progressLive: LiveData<Boolean> get() = _progressLive

    private val _citiesLive = MutableLiveData<List<CitiesModel>>()
    val citiesLive: LiveData<List<CitiesModel>> get() = _citiesLive
    fun getCities() {
        viewModelScope.launch {
            val result = repo.getCities()
            when(result) {
                is BaseResponse.Success -> {
                    _citiesLive.postValue(result.data)
                }
                is BaseResponse.Error -> {
                    _failLive.postValue(result.message)
                }
            }
        }
    }

    fun postInfo(
        firstName: String?,
        lastName: String?,
        patronymic: String?,
        cityPk: String,
        email: String
    ) {
        viewModelScope.launch {
            _progressLive.postValue(true)
            val result = repo.postCompletion(firstName, lastName, patronymic, cityPk, email)
            when(result) {
                is BaseResponse.Success -> {
                    _successLive.postValue(Unit)
                }
                is BaseResponse.Error -> {
                    _failLive.postValue(result.message)
                }
            }
            _progressLive.postValue(false)
        }
    }
}