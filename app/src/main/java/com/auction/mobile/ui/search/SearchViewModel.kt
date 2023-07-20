package com.auction.mobile.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auction.mobile.data.use_case.CreateBidUseCase
import com.auction.mobile.data.use_case.GetAllAuctionUseCase
import com.auction.mobile.domain.models.BaseResponse
import com.auction.mobile.domain.models.BidCreateRequestModel
import com.auction.mobile.domain.models.FavoriteProductModel
import com.auction.mobile.domain.models.ProductModel
import com.auction.mobile.domain.repository.ProductRepository
import com.auction.mobile.tools.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repo: ProductRepository,
    private val getAllProductUseCase: GetAllAuctionUseCase,
    private val createBidUseCase: CreateBidUseCase
) : ViewModel() {

    private val _successLive = MutableLiveData<Unit>()
    val successLive: LiveData<Unit> get() = _successLive

    private val _failLive = com.auction.mobile.tools.SingleLiveEvent<String>()
    val failLive: LiveData<String> get() = _failLive

    private val _progressLive = MutableLiveData<Boolean>()
    val progressLive: LiveData<Boolean> get() = _progressLive

    private val _productLive = MutableLiveData<List<FavoriteProductModel>>()
    val productLive: LiveData<List<FavoriteProductModel>> get() = _productLive

    fun createBid(id: String) {
        viewModelScope.launch {
            _progressLive.postValue(true)
            val res = createBidUseCase.execute(BidCreateRequestModel(id))
            when(res) {
                is BaseResponse.Success -> {
                    _successLive.postValue(Unit)
                }
                is BaseResponse.Error -> {
                    _failLive.postValue(res.message)
                }
            }
            _progressLive.postValue(false)
        }
    }
    fun getProducts(
        q: String,
        sort: String,
        price_min: String,
        price_max: String,
        city: String
    ) {
        viewModelScope.launch {
            _progressLive.postValue(true)
            val res = getAllProductUseCase.invoke(
                q, sort, price_min, price_max, city
            )
            _productLive.postValue(res)
//            val result = repo.getProducts(q, sort)
//            when (result) {
//                is BaseResponse.Success -> {
//                    _productLive.postValue(result.data)
//                }
//                is BaseResponse.Error -> {
//                    _failLive.postValue(result.message)
//                }
//            }
            _progressLive.postValue(false)
        }
    }

    fun deleteProductFromFavorite(info: ProductModel) {
        viewModelScope.launch {
            repo.deleteProduct(info)
        }
    }

    fun insertToDb(item: ProductModel) {
        viewModelScope.launch {
            val result = repo.insertInFavorite(item)
            when (result) {
                is BaseResponse.Success -> {

                }
                is BaseResponse.Error -> {

                }
            }
        }
    }


}