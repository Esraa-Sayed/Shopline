package com.eCommerce.shopify.ui.register.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eCommerce.shopify.model.CustomerResponse
import com.eCommerce.shopify.ui.register.repo.RegisterRepoInterface
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.eCommerce.shopify.model.error.Error

class RegisterViewModel(private val repo: RegisterRepoInterface):ViewModel() {

    private val _customerResponse = MutableLiveData<CustomerResponse>()
    val customerRespoonse:LiveData<CustomerResponse> = _customerResponse

    private var _errorMsgResponse = MutableLiveData<String>()
    val errorMsgResponse: LiveData<String> = _errorMsgResponse

    private val coroutineExceptionHandler = CoroutineExceptionHandler{ _, t ->
        run {
            t.printStackTrace()
            _errorMsgResponse.postValue(t.message)
        }
    }

    fun postNewCustomer(customer: CustomerResponse){
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler){
            val newResponseCustomer = repo.registerCustomer(customer)

            if(newResponseCustomer.isSuccessful){
                _customerResponse.postValue(newResponseCustomer.body())
            }
            else{
                //_errorMsgResponse.postValue("")
                val jsonError = newResponseCustomer.errorBody()?.string() as String
                val gson = Gson()
                val errorObj = gson.fromJson(jsonError,Error::class.java)
                val errorMsg = "there is some issuses in your registration:"
                var emailError = ""
                var phoneError = ""
                if(!errorObj.errors?.email.isNullOrEmpty()){
                    emailError = "about the email ${errorObj.errors?.email?.get(0)}"
                }
                if(!errorObj.errors?.phone.isNullOrEmpty()){
                    phoneError = "about the phone ${errorObj.errors?.phone?.get(0)}"
                }
                Log.i("TAG", "in vieeeeeewwww  moooodellll : $errorMsg $emailError $phoneError")
                _errorMsgResponse.postValue("$errorMsg $emailError $phoneError")
                /*try {
                    if (_errorMsgResponse.value.isNullOrEmpty()){
                        val str = "$errorMsg $emailError $phoneError"
                        _errorMsgResponse.postValue(str)
                    }
                }
                catch (e:NullPointerException){
                    _errorMsgResponse.postValue("$errorMsg $emailError $phoneError")
                }*/

            }
        }
    }

    fun saveDataInSharedPref(context: Context,email:String,userId: Long, userName:String){
        repo.saveDataInSharedPref(context,email,userId,userName)
    }

}