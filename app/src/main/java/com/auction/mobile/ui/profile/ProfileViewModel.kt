package com.auction.mobile.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auction.mobile.domain.models.BaseResponse
import com.auction.mobile.domain.models.GetUserInfoModel
import com.auction.mobile.domain.models.ProductModel
import com.auction.mobile.domain.repository.ProfileRepository
import com.auction.mobile.domain.repository.WonAuctionsRepository
import com.auction.mobile.tools.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
private val repo: ProfileRepository,
private val wonRepo: WonAuctionsRepository
): ViewModel() {

    private val _progressLive = MutableLiveData<Boolean>()
    val progressLive: LiveData<Boolean> get() = _progressLive

    private val _infoLive = MutableLiveData<GetUserInfoModel>()
    val infoLive: LiveData<GetUserInfoModel> get() = _infoLive

    private val _failLive = com.auction.mobile.tools.SingleLiveEvent<String>()
    val failLive: LiveData<String> get() = _failLive

    private val _successLive = MutableLiveData<Unit>()
    val successLive: LiveData<Unit> get() = _successLive

    private val _productLive = MutableLiveData<List<ProductModel>>()
    val productLive: LiveData<List<ProductModel>> get() = _productLive

    fun getProducts() {
        viewModelScope.launch {
            _progressLive.postValue(true)
            val res = wonRepo.getAllWonProducts()
            when (res) {
                is BaseResponse.Success -> {
                    _productLive.postValue(res.data)
                }
                is BaseResponse.Error -> {
                    _failLive.postValue(res.message)
                }
            }
            _progressLive.postValue(false)
        }
    }

    fun getInfo() {
        viewModelScope.launch {
            _progressLive.postValue(true)
            val result = repo.getProfileInfo()
            when(result) {
                is BaseResponse.Success -> {
                    _infoLive.postValue(result.data)
                }
                is BaseResponse.Error -> {
                    _failLive.postValue(result.message)
                }
            }
            _progressLive.postValue(false)
        }
    }


}