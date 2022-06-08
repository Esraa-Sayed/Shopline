package com.eCommerce.shopify.ui.add_address.view_model

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eCommerce.shopify.model.Addresse
import com.eCommerce.shopify.model.AddressesUserModel
import com.eCommerce.shopify.model.PostAddress
import com.eCommerce.shopify.ui.setting.repo.AddAddressRepoInterface
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddAddressViewModel(private  val _repo: AddAddressRepoInterface) : ViewModel() {

    private var _errorMsgResponse = MutableLiveData<String>()
    val errorMsgResponse: LiveData<String> = _errorMsgResponse

    private val coroutineExceptionHandler = CoroutineExceptionHandler{ _, t ->
        run {
            t.printStackTrace()
            _errorMsgResponse.postValue(t.message)
        }
    }
    fun getUserId(context: Context): Long{
        return _repo.getUserIdFromSharedPref(context, 0L)
    }
    fun addAddress(context: Context, address: PostAddress){
        viewModelScope.launch(Dispatchers.IO) {
            val response = _repo.addAddress(getUserId(context), address)
            Log.i("ADD_ADDRESS", response.message().toString())
        }
    }
}