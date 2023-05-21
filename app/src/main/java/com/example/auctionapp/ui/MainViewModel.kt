package com.example.auctionapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auctionapp.data.use_case.GetCitiesUseCase
import com.example.auctionapp.domain.models.CitiesModel
import com.example.auctionapp.domain.models.City
import com.example.auctionapp.domain.models.ProductModel
import com.example.auctionapp.domain.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
private val repo: MainRepository,
private val getCitiesUseCase: GetCitiesUseCase
): ViewModel() {


    private val _citiesLiveData = MutableLiveData<List<CitiesModel>>()
    val citiesLiveData: LiveData<List<CitiesModel>> get() = _citiesLiveData

    private val _liveFavoriteItems = MutableLiveData<List<ProductModel>>()
    val liveFavoriteItems: LiveData<List<ProductModel>> get() = _liveFavoriteItems

    private val _minPriceLiveData = MutableLiveData<String>()
    val minPriceLiveData: LiveData<String> get() = _minPriceLiveData

    private val _maxPriceLiveData = MutableLiveData<String>()
    val maxPriceLiveData: LiveData<String> get() = _maxPriceLiveData

    private val _selectedCityLiveData = MutableLiveData<String>()
    val selectedCityLiveData: LiveData<String> get() = _selectedCityLiveData

    val selectedCityNameLiveData = MutableLiveData<String>()

    var selectedItemPosition: Int? = null

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

    fun setMinPrice(price: String?) {
        _minPriceLiveData.postValue(price ?: "")
    }

    fun setCity(cityId: String) {
        _selectedCityLiveData.postValue(cityId)
    }

    fun setMaxPrice(price: String?) {
        _maxPriceLiveData.postValue(price ?: "")
    }

    fun getCities() {
        viewModelScope.launch {
            _citiesLiveData.postValue(getCitiesUseCase.invoke())
        }
    }

}