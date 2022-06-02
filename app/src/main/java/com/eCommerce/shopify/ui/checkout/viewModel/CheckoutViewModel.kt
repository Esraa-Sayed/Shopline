package com.eCommerce.shopify.ui.checkout.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eCommerce.shopify.model.Customer
import com.eCommerce.shopify.model.orderDetails.Order
import com.eCommerce.shopify.model.orderDetails.OrderDetails
import com.eCommerce.shopify.ui.checkout.repo.CheckoutRepoInterface
import com.eCommerce.shopify.utils.AppConstants
import com.eCommerce.shopify.utils.AppSharedPref
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CheckoutViewModel(var _repo: CheckoutRepoInterface) : ViewModel() {
    private var _postOrderResponse = MutableLiveData<OrderDetails>()
    val postOrderResponse: LiveData<OrderDetails> = _postOrderResponse

    private var _errorMsgResponse = MutableLiveData<String>()
    val errorMsgResponse: LiveData<String> = _errorMsgResponse

    private val coroutineExceptionHandler = CoroutineExceptionHandler{ _, t ->
        run {
            t.printStackTrace()
            _errorMsgResponse.postValue(t.message)
        }
    }

    fun postOrderWithUserIdAndEmail(order: Order,context: Context) {

        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            val userEmailAndId = getUserEmailAndId(context)
            val customer = Customer(id = userEmailAndId.second, email = userEmailAndId.first)
            order.customer = customer
            val orderResponse = _repo.postOrder(order)
            if (orderResponse.isSuccessful) {
                _postOrderResponse.postValue(orderResponse.body())
            } else {
                _errorMsgResponse.postValue(orderResponse.message())
            }
        }
    }
    private fun getUserEmailAndId(context: Context):Pair<String,Long>{
        val shared = AppSharedPref.getInstance(context,AppConstants.PREFRENCE_File)
        val email = shared.getStringValue(AppConstants.USER_EMAIL,"not found")
        val id = shared.getLongValue(AppConstants.USER_ID,0)
        return Pair(email,id)
    }
}