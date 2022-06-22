package com.eCommerce.shopify.ui.main.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eCommerce.shopify.model.ProductDetail
import com.eCommerce.shopify.ui.main.repo.MainRepoInterface
import kotlinx.coroutines.CoroutineExceptionHandler

class MainViewModel(private val _repo: MainRepoInterface) : ViewModel() {

    private var _errorMsgResponse = MutableLiveData<String>()
    val errorMsgResponse: LiveData<String> = _errorMsgResponse

    private val coroutineExceptionHandler = CoroutineExceptionHandler{ _, t ->
        run {
            t.printStackTrace()
            _errorMsgResponse.postValue(t.message)
        }
    }

    fun getAllProductInShoppingCartList(): LiveData<List<ProductDetail>> {
        return _repo.allProductInShoppingCart
    }

    fun isUserLogin(context: Context): Boolean {
        return _repo.isUserLogin(context)
    }
}