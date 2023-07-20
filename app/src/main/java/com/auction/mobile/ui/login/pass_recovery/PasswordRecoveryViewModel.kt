package com.auction.mobile.ui.login.pass_recovery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auction.mobile.domain.models.BaseResponse
import com.auction.mobile.domain.repository.RecoveryPasswordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordRecoveryViewModel @Inject constructor(
private val repo: RecoveryPasswordRepository
): ViewModel() {

    private val _resetPassSuccess = MutableLiveData<Unit>()
    val resetPassSuccess: LiveData<Unit> get() = _resetPassSuccess


    private val _resetPassFail = MutableLiveData<String>()
    val resetPassFail: LiveData<String> get() = _resetPassFail

    fun passReset(userName: String, password1: String, password2: String) {
        viewModelScope.launch {
            val result = repo.postNewPassword(userName, password1, password2)
            when(result) {
                is BaseResponse.Success -> {
                    _resetPassSuccess.postValue(Unit)
                }
                is BaseResponse.Error -> {
                    _resetPassFail.postValue(result.message)
                }
            }
        }
    }
}