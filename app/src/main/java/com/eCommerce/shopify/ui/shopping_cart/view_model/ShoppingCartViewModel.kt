package com.eCommerce.shopify.ui.shopping_cart.view_model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eCommerce.shopify.model.ProductDetail
import com.eCommerce.shopify.ui.shopping_cart.repo.ShoppingCartRepoInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShoppingCartViewModel(val context: Context, val repo: ShoppingCartRepoInterface, var products: MutableList<ProductDetail>) : ViewModel() {

    private var mutableTotalPrice: MutableLiveData<Double> = MutableLiveData()
    val totalPrice: LiveData<Double> = mutableTotalPrice

    init {
        getTotalPrice(products)
    }
    fun getIsLogin(): Boolean {
        return repo.getIsLogin(context = context)
    }
    fun updateProductInShoppingCart(productDetail: ProductDetail) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.updateProductInShoppingCart(productDetail)
        }

    }

    fun deleteProductFromShoppingCart(productDetail: ProductDetail){
        viewModelScope.launch(Dispatchers.IO) {
            updateTotalPrice(productDetail.variants[0].price.toDouble() * productDetail.amount, "-")
            repo.deleteProductFromShoppingCart(productDetail)
        }
    }

    fun getCurrency(): String{
        return repo.getCurrencyFromSharedPref(context = context)
    }
    private fun getTotalPrice(productDetail: List<ProductDetail>){
        var sumOfPrice = 0.0
        if(productDetail.isNotEmpty()){
            for (item in productDetail){
                sumOfPrice += (item.variants[0].price.toDouble() * item.amount)
            }
            mutableTotalPrice.postValue(getPriceWithCurrency(sumOfPrice))
        }
        else{
            mutableTotalPrice.postValue(00.00)
        }
    }

    fun updateTotalPrice(amountOfPrice: Double, operation: String){
        when(operation){
            "+" -> mutableTotalPrice.postValue(mutableTotalPrice.value?.plus(getPriceWithCurrency(amountOfPrice)))
            "-" -> mutableTotalPrice.postValue(mutableTotalPrice.value?.minus(getPriceWithCurrency(amountOfPrice)))
        }
    }

    private fun getPriceWithCurrency(price: Double): Double{
        var priceTemp = price
        val currency = getCurrency()
        if(currency != "EGP"){
            priceTemp /= 20
        }
        return priceTemp
    }
    fun getPriceWithCurrencyAsString(price: Double): String{
        val currency = getCurrency()
        return String.format("%.2f", price).plus(" ").plus(currency)
    }

    fun deleteProduct(productDetail: ProductDetail) {
        if (products.size >= 2){
            products.remove(productDetail)
            getTotalPrice(products)
        }
        else{
            products = mutableListOf()
        }
    }
}