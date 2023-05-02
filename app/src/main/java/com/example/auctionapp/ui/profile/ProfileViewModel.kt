package com.example.auctionapp.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auctionapp.domain.models.BaseResponse
import com.example.auctionapp.domain.models.GetUserInfoModel
import com.example.auctionapp.domain.repository.ProfileRepository
import com.example.auctionapp.tools.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
private val repo: ProfileRepository
): ViewModel() {

    private val _progressLive = MutableLiveData<Boolean>()
    val progressLive: LiveData<Boolean> get() = _progressLive

    private val _infoLive = MutableLiveData<GetUserInfoModel>()
    val infoLive: LiveData<GetUserInfoModel> get() = _infoLive

    private val _failLive = SingleLiveEvent<String>()
    val failLive: LiveData<String> get() = _failLive


    fun getInfo() {
        viewModelScope.launch {
            _progressLive.postValue(true)
            val result = repo.getProfileInfo()
            when(result) {
                is BaseResponse.Success -> {
                    _infoLive.postValue(result.data)
                }
                is BaseResponse.Error -> {
                    _failLive.postValue(result.message)
                }
            }
            _progressLive.postValue(false)
        }
    }
}