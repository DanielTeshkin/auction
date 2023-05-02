package com.example.auctionapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auctionapp.domain.models.ProductModel
import com.example.auctionapp.domain.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
private val repo: MainRepository
): ViewModel() {

    private val _liveFavoriteItems = MutableLiveData<List<ProductModel>>()
    val liveFavoriteItems: LiveData<List<ProductModel>> get() = _liveFavoriteItems

    fun getAllFavorite(result: () -> Unit) {
        viewModelScope.launch {
            kotlin.runCatching {
                repo.getAllFavorite()
            }.onSuccess {
                _liveFavoriteItems.postValue(it)
                result()
            }.onFailure {
                result()
            }
        }
    }

}