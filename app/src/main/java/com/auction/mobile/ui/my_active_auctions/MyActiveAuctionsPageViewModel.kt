package com.auction.mobile.ui.my_active_auctions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auction.mobile.domain.models.BaseResponse
import com.auction.mobile.domain.models.PhotosModel
import com.auction.mobile.domain.models.ProductModel
import com.auction.mobile.domain.models.RacePriceModel
import com.auction.mobile.domain.repository.DetailInfoRepository
import com.auction.mobile.tools.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyActiveAuctionsPageViewModel @Inject constructor(
    private val repo: DetailInfoRepository
): ViewModel() {

    private val _successLive = MutableLiveData<Unit>()
    val successLive: LiveData<Unit> get() = _successLive

    private val _raisePriceLive = MutableLiveData<Boolean>()
    val raisePriceLive: LiveData<Boolean> get() = _raisePriceLive

    private val _failLive = com.auction.mobile.tools.SingleLiveEvent<String>()
    val failLive: LiveData<String> get() = _failLive

    private val _progressLive = MutableLiveData<Boolean>()
    val progressLive: LiveData<Boolean> get() = _progressLive

    private val _photosFlow = MutableStateFlow<List<PhotosModel>>(emptyList())
    val photosFlow: StateFlow<List<PhotosModel>> get() = _photosFlow

    private val _infoFlow = MutableLiveData<ProductModel>()
    val infoFlow: MutableLiveData<ProductModel> get() = _infoFlow


    fun racePrice(id: String, info: RacePriceModel) {
        viewModelScope.launch {
            _progressLive.postValue(true)
            val result = repo.racePrice(id = id, info = info)
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

    fun getDetailInfo(id: String, delay: Long) {
        viewModelScope.launch(Dispatchers.IO) {
                delay(delay)
                val result = repo.getDetailInfo(id)
                when (result) {
                    is BaseResponse.Success -> {
                        result.data.photos?.let { _photosFlow.emit(it) }
                        _infoFlow.postValue(result.data)
                    }
                    is BaseResponse.Error -> {
                        _failLive.postValue(result.message)
                    }
                }
        }
    }
}