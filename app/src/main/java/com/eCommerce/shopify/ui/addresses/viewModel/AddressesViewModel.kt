package com.eCommerce.shopify.ui.addresses.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eCommerce.shopify.model.AddressesUserModel
import com.eCommerce.shopify.ui.addresses.repo.AddressesRepoInterface
import com.eCommerce.shopify.utils.AppConstants
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddressesViewModel(var _repo: AddressesRepoInterface) : ViewModel() {
    private var _UserAddresses = MutableLiveData<AddressesUserModel>()
    val UserAddresses: LiveData<AddressesUserModel> = _UserAddresses

    private var _errorMsgResponse = MutableLiveData<String>()
    val errorMsgResponse: LiveData<String> = _errorMsgResponse

    private val coroutineExceptionHandler = CoroutineExceptionHandler{ _, t ->
        run {
            t.printStackTrace()
            _errorMsgResponse.postValue(t.message)
        }
    }

    fun getUserAddresses(context: Context) {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            val userId = _repo.getUserIdFromSharedPref(context, AppConstants.USER_ID,0)
            val user = _repo.getUserAddressesWithId(userId)
            if (user.isSuccessful) {
                _UserAddresses.postValue(user.body())
            } else {
                _errorMsgResponse.postValue(user.message())
            }
        }
    }
}