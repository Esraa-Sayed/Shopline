package com.eCommerce.shopify.ui.register.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eCommerce.shopify.model.CustomerResponse
import com.eCommerce.shopify.ui.register.repo.RegisterRepoInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel(private val repo: RegisterRepoInterface):ViewModel() {

    private val _customerResponse = MutableLiveData<CustomerResponse>()
    val customerRespoonse:LiveData<CustomerResponse> = _customerResponse

    private var _errorMsgResponse = MutableLiveData<String>()
    val errorMsgResponse: LiveData<String> = _errorMsgResponse

    fun postNewCustomer(customer: CustomerResponse){
        viewModelScope.launch(Dispatchers.IO){
            val newResponseCustomer = repo.registerCustomer(customer)

            if(newResponseCustomer.isSuccessful){
                _customerResponse.postValue(newResponseCustomer.body())
            }
            else{
                _errorMsgResponse.postValue(newResponseCustomer.message())
            }
        }
    }
}