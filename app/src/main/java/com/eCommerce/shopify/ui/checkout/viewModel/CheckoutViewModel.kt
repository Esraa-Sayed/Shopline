package com.eCommerce.shopify.ui.checkout.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eCommerce.shopify.model.AddressesUserModel
import com.eCommerce.shopify.model.Customer
import com.eCommerce.shopify.model.ProductDetail
import com.eCommerce.shopify.model.discount.DiscountCodes
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

    private var _getDiscountCodesResponse = MutableLiveData<DiscountCodes>()
    val getDiscountCodesResponse: LiveData<DiscountCodes> = _getDiscountCodesResponse

    private var _userAddresses = MutableLiveData<AddressesUserModel>()
    val userAddresses: LiveData<AddressesUserModel> = _userAddresses

    private val coroutineExceptionHandler = CoroutineExceptionHandler{ _, t ->
        run {
            t.printStackTrace()
            _errorMsgResponse.postValue(t.message)
        }
    }

    fun postOrderWithUserIdAndEmail(order: Order,context: Context) {

        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            val userEmailAndId = getUserEmailAndId(context)
            val customer = Customer(id = userEmailAndId.third, email = userEmailAndId.first, first_name = userEmailAndId.second)
            order.customer = customer
            val orderResponse = _repo.postOrder(order)
            if (orderResponse.isSuccessful) {
                _postOrderResponse.postValue(orderResponse.body())
            } else {
                _errorMsgResponse.postValue(orderResponse.message())
            }
        }
    }
    fun getDiscountCodes(){
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            val getDiscountCodesResponse = _repo.getDiscountCodes()
            if (getDiscountCodesResponse.isSuccessful) {
                _getDiscountCodesResponse.postValue(getDiscountCodesResponse.body())
            } else {
                _errorMsgResponse.postValue(getDiscountCodesResponse.message())
            }
        }
    }
    fun getCurrency(context: Context): String{
        return  _repo.getCurrency(context)
    }
    fun getUserId(context: Context): Long{
        return  _repo.getUserID(context)
    }
    private fun getUserEmailAndId(context: Context):Triple<String,String,Long>{
        val shared = AppSharedPref.getInstance(context,AppConstants.PREFRENCE_File)
        val email = shared.getStringValue(AppConstants.USER_EMAIL,"not found")
        val name = shared.getStringValue(AppConstants.USER_NAME,"not found")
        val id = shared.getLongValue(AppConstants.USER_ID,0)
        return Triple(email,name,id)
    }

    fun deleteCheckOutList(productDetail: Array<ProductDetail>) {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            _repo.deleteCheckOutList(productDetail)
        }
    }
    fun getUserAddresses(context: Context) {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            val userId = _repo.getUserID(context)
            val user = _repo.getUserAddressesWithId(userId)
            if (user.isSuccessful) {
                _userAddresses.postValue(user.body())
            } else {
                _errorMsgResponse.postValue(user.message())
            }
        }
    }
}