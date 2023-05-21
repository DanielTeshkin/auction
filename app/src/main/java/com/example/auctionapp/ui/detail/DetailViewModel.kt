package com.example.auctionapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auctionapp.domain.models.*
import com.example.auctionapp.domain.repository.DetailInfoRepository
import com.example.auctionapp.tools.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    val repo: DetailInfoRepository
) : ViewModel() {

    private val _successLive = MutableLiveData<Unit>()
    val successLive: LiveData<Unit> get() = _successLive

    private val _raisePriceLive = MutableLiveData<Boolean>()
    val raisePriceLive: LiveData<Boolean> get() = _raisePriceLive

    private val _failLive = SingleLiveEvent<String>()
    val failLive: LiveData<String> get() = _failLive

    private val _progressLive = MutableLiveData<Boolean>()
    val progressLive: LiveData<Boolean> get() = _progressLive

    private val _photosFlow = MutableStateFlow<List<PhotosModel>>(emptyList())
    val photosFlow: StateFlow<List<PhotosModel>> get() = _photosFlow

    private val _infoFlow = MutableStateFlow<ProductModel?>(null)
    val infoFlow: StateFlow<ProductModel?> get() = _infoFlow

    fun getDetailInfo(id: String) {
        viewModelScope.launch {
            _progressLive.postValue(true)
            val result = repo.getDetailInfo(id)
            when (result) {
                is BaseResponse.Success -> {
                    result.data.photos?.let { _photosFlow.emit(it) }
                    _infoFlow.emit(result.data)
                }
                is BaseResponse.Error -> {
                    _failLive.postValue(result.message)
                }
            }
            _progressLive.postValue(false)
        }
    }

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

    fun createBid(id: String) {
        viewModelScope.launch {
            _progressLive.postValue(true)
            val result = repo.createBid(info = BidCreateRequestModel(id))
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

    fun deleteProductFromFavorite(info: ProductModel) {
        viewModelScope.launch {
            repo.deleteProduct(info)
        }
    }

    private val _favoriteLive = MutableLiveData<List<ProductModel>>()
    val favoriteLive: LiveData<List<ProductModel>> get() = _favoriteLive

    fun getFavorite() {
        viewModelScope.launch {
            _favoriteLive.postValue(repo.getAllFavorite())
        }
    }

    fun insertToDb(item: ProductModel) {
        viewModelScope.launch {
            repo.insertInFavorite(item)
        }
    }

//    fun raisePrice(id: String, price: String, success: () -> Unit, fail: () -> Unit) {
//        viewModelScope.launch {
//            _progressLive.postValue(true)
//            val result = repo.raisePrice(id, price)
//            when (result) {
//                is BaseResponse.Success -> {
//                    _raisePriceLive.postValue(true)
//                    success()
//                }
//                is BaseResponse.Error -> {
//                    fail()
//                    _failLive.postValue(result.message)
//                }
//            }
//            _progressLive.postValue(false)
//        }
//    }
}