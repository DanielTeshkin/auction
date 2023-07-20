package com.auction.mobile.ui.my_applications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auction.mobile.domain.models.BaseResponse
import com.auction.mobile.domain.models.MyBidModel
import com.auction.mobile.domain.repository.MyApplicationsRepository
import com.auction.mobile.tools.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyApplicationsViewModel @Inject constructor(
private val repo: MyApplicationsRepository
): ViewModel() {

    private val _progressLive = MutableLiveData<Boolean>()
    val progressLive: LiveData<Boolean> get() = _progressLive

    private val _productLive = MutableLiveData<List<MyBidModel>>()
    val productLive: LiveData<List<MyBidModel>> get() = _productLive

    private val _failLive = com.auction.mobile.tools.SingleLiveEvent<String>()
    val failLive: LiveData<String> get() = _failLive

    init {
        getItems()
    }

    fun getItems() {
        viewModelScope.launch {
            _progressLive.postValue(true)
            val result = repo.getBidProducts()
            when (result) {
                is BaseResponse.Success -> {
                    _productLive.postValue(result.data)
                }
                is BaseResponse.Error -> {
                    _failLive.postValue(result.message)
                }
            }
            _progressLive.postValue(false)
        }
    }

}