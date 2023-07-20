package com.auction.mobile.ui.win_auctions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auction.mobile.domain.models.BaseResponse
import com.auction.mobile.domain.models.ProductModel
import com.auction.mobile.domain.repository.WonAuctionsRepository
import com.auction.mobile.tools.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WinAuctionsViewModel @Inject constructor(
private val repo: WonAuctionsRepository
): ViewModel() {

    private val _successLive = MutableLiveData<Unit>()
    val successLive: LiveData<Unit> get() = _successLive

    private val _failLive = com.auction.mobile.tools.SingleLiveEvent<String>()
    val failLive: LiveData<String> get() = _failLive

    private val _progressLive = MutableLiveData<Boolean>()
    val progressLive: LiveData<Boolean> get() = _progressLive

    private val _productLive = MutableLiveData<List<ProductModel>>()
    val productLive: LiveData<List<ProductModel>> get() = _productLive

    fun getProducts() {
        viewModelScope.launch {
            _progressLive.postValue(true)
            val res = repo.getAllWonProducts()
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

}