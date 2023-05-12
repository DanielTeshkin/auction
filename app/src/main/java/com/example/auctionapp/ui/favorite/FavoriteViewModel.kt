package com.example.auctionapp.ui.favorite

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auctionapp.domain.models.*
import com.example.auctionapp.domain.repository.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repo: FavoriteRepository,
) : ViewModel() {

    private val _liveItems = MutableLiveData<List<FavoriteProductModel>>()
    val liveItems: LiveData<List<FavoriteProductModel>> get() = _liveItems

    fun getAll() {
        viewModelScope.launch {
                val res = repo.getAllFavorite()
            when(res) {
                is BaseResponse.Success -> {
                    _liveItems.postValue(res.data.map { it.toFavoriteModel(true) })
                }
                is BaseResponse.Error -> {
Log.d("TTT", res.message.toString())
                }
            }
        }
    }

    fun deleteProductFromFavorite(info: ProductModel) {
        viewModelScope.launch {
            repo.deleteProduct(info)
        }
    }
}