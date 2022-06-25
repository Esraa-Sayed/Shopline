package com.eCommerce.shopify.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eCommerce.shopify.database.shoppingcart.ShoppingCartLocalSourceInterface
import com.eCommerce.shopify.model.ProductDetail

class FakeShoppingCartLocalSource: ShoppingCartLocalSourceInterface {

    private val observableProductDetailList = MutableLiveData<List<ProductDetail>>()
    private val observableProductDetail = MutableLiveData<ProductDetail>()

    private var productDetailList: List<ProductDetail>? = null

    constructor(productDetailList: List<ProductDetail>) {
        this.productDetailList = productDetailList
        observableProductDetailList.postValue(this.productDetailList)
        observableProductDetail.postValue(productDetailList[0])
    }

    override val allProductInShoppingCart: LiveData<List<ProductDetail>>
        get() = observableProductDetailList

    override fun getProductInShoppingCart(id: Long): LiveData<ProductDetail> {
        return observableProductDetail
    }

    override fun insertProductInShoppingCart(productDetail: ProductDetail) {
        TODO("Not yet implemented")
    }

    override fun deleteProductFromShoppingCart(productDetail: ProductDetail) {
        TODO("Not yet implemented")
    }
}