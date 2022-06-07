package com.eCommerce.shopify.ui.add_address.view_model

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eCommerce.shopify.ui.setting.repo.AddAddressRepoInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddAddressViewModel(private  val _repo: AddAddressRepoInterface) : ViewModel() {

    fun addAddress(id: Long){
        viewModelScope.launch(Dispatchers.IO) {
            val response = _repo.addAddress(id)
            Log.i("ADD_ADDRESS", response.message())
        }
    }
}