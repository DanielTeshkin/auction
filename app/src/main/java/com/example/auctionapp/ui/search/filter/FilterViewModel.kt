package com.example.auctionapp.ui.search.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auctionapp.data.use_case.GetCitiesUseCase
import com.example.auctionapp.domain.models.CitiesModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
private val getCitiesUseCase: GetCitiesUseCase
): ViewModel() {

    private val _citiesLiveData = MutableLiveData<List<CitiesModel>>()
    val citiesLiveData: LiveData<List<CitiesModel>> get() = _citiesLiveData

    fun getCities() {
        viewModelScope.launch {
            _citiesLiveData.postValue(getCitiesUseCase.invoke())
        }
    }
}