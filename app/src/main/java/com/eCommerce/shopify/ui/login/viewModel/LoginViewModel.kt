package com.eCommerce.shopify.ui.login.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eCommerce.shopify.model.UserData
import com.eCommerce.shopify.ui.login.repo.LoginRepoInterface
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(var _repo: LoginRepoInterface) : ViewModel() {
    private var _UserDataResponse = MutableLiveData<UserData>()
    val UserDataResponse: LiveData<UserData> = _UserDataResponse

    private var _errorMsgResponse = MutableLiveData<String>()
    val errorMsgResponse: LiveData<String> = _errorMsgResponse

    private val coroutineExceptionHandler = CoroutineExceptionHandler{ _, t ->
        run {
            t.printStackTrace()
            _errorMsgResponse.postValue(t.message)
        }
    }

    fun getUserData(email:String) {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            val user = _repo.getUserDataWithEmail(email)
            if (user.isSuccessful) {
                _UserDataResponse.postValue(user.body())
            } else {
                _errorMsgResponse.postValue(user.message())
            }
        }
    }
}