package com.example.auctionapp.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auctionapp.data.model.ProductDTO
import com.example.auctionapp.domain.models.BaseResponse
import com.example.auctionapp.domain.models.ProductModel
import com.example.auctionapp.domain.repository.ProductRepository
import com.example.auctionapp.tools.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
private val repo: ProductRepository
): ViewModel() {

    private val _successLive = MutableLiveData<Unit>()
    val successLive: LiveData<Unit> get() = _successLive

    private val _failLive = SingleLiveEvent<String>()
    val failLive: LiveData<String> get() = _failLive

    private val _progressLive = MutableLiveData<Boolean>()
    val progressLive: LiveData<Boolean> get() = _progressLive

    private val _productLive = MutableLiveData<List<ProductModel>>()
    val productLive: LiveData<List<ProductModel>> get() = _productLive
    fun getProducts(q: String, sort: String) {
        viewModelScope.launch {
            _progressLive.postValue(true)
            val result = repo.getProducts(q, sort)
            when(result) {
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