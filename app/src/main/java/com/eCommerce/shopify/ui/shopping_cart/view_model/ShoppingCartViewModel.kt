package com.eCommerce.shopify.ui.shopping_cart.view_model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eCommerce.shopify.model.ProductDetail
import com.eCommerce.shopify.ui.shopping_cart.repo.ShoppingCartRepoInterface
import com.eCommerce.shopify.utils.AppConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShoppingCartViewModel(val context: Context, val repo: ShoppingCartRepoInterface, var products: MutableList<ProductDetail>) : ViewModel() {

    private var mutableTotalPrice: MutableLiveData<String> = MutableLiveData()
    val totalPriceAsString: LiveData<String> = mutableTotalPrice

    init {
        getTotalPriceAsString(products)
    }
    fun getIsLogin(): Boolean {
        return repo.getIsLogin(context = context)
    }
    fun insertProductInShoppingCart(productDetail: ProductDetail) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.insertProductInShoppingCart(productDetail)
        }

    }

    fun deleteProductFromShopingCart(productDetail: ProductDetail){
        viewModelScope.launch(Dispatchers.IO) {
            updatePrice(productDetail.variants[0].price.toDouble() * productDetail.amount, "-")
            repo.deleteProductFromShopingCart(productDetail)
        }
    }

    fun getCurrency(): String{
        return repo.getCurrencyFromSharedPref(context = context)
    }
    fun getTotalPriceAsString(productDetail: List<ProductDetail>){
        var currency = getCurrency()
        var sumOfPrice = 0.0
        if(productDetail.size > 0){
            for (item in productDetail){
                sumOfPrice += item.variants[0].price.toDouble() * item.amount
            }
            var priceMultiplier = 1.0
            if(currency != AppConstants.EGP){
                priceMultiplier /= 10
            }
            val priceDouble = sumOfPrice * priceMultiplier
            val totalPrice = String.format("%.2f", priceDouble) + " " + currency
            mutableTotalPrice.postValue(totalPrice)
        }
        else{
            mutableTotalPrice.postValue("00.00")
        }
    }

    fun updatePrice(totalAmount: Double, operation: String){
        when(operation){
            "-" -> mutableTotalPrice.postValue((mutableTotalPrice.value?.split(" ")?.get(0)?.toDouble()
                ?.minus(totalAmount)).toString() + " " + getCurrency())
            "+" -> mutableTotalPrice.postValue((mutableTotalPrice.value?.split(" ")?.get(0)?.toDouble()
                ?.plus(totalAmount)).toString() + " " + getCurrency())
        }

    }



    fun deleteProduct(productDetail: ProductDetail) {
        if (products.size >= 2){
            products.remove(productDetail)
            getTotalPriceAsString(products)
        }
        else{
            products = mutableListOf()
        }
    }
}