package com.auction.mobile.ui.my_active_auctions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auction.mobile.domain.models.BaseResponse
import com.auction.mobile.domain.models.ProductModel
import com.auction.mobile.domain.repository.ActiveAuctionsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyActiveAuctionsViewModel @Inject constructor(
    private val repo: ActiveAuctionsRepository,
//    private val repo1: ProductRepository
) : ViewModel() {

    private val _progressLive = MutableLiveData<Boolean>()
    val progressLive: LiveData<Boolean> get() = _progressLive

    private val _productLive = MutableLiveData<List<ProductModel>>()
    val productLive: LiveData<List<ProductModel>> get() = _productLive

    fun getProducts() {
        viewModelScope.launch {
            _progressLive.postValue(true)
            val res = repo.getActiveAuctions()
//            val res = repo1.getProducts("", "", "", "", "")
            when (res) {
                is BaseResponse.Success -> {
                    _productLive.postValue(res.data)
                }
                is BaseResponse.Error -> {

                }
            }
            _progressLive.postValue(false)
        }
    }

}