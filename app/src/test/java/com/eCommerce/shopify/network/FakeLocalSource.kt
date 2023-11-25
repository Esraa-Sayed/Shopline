package com.eCommerce.shopify.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eCommerce.shopify.database.favorite.LocalSourceInterface
import com.eCommerce.shopify.model.Product

class FakeLocalSource: LocalSourceInterface {

    private val observableProduct = MutableLiveData<Product>()

    private var product: Product? = null

    constructor(product: Product) {
        this.product = product
        observableProduct.postValue(this.product)
    }

    override fun getAllFavorites(): LiveData<List<Product>> {
        TODO("Not yet implemented")
    }

    override fun getFavoriteProduct(id: Long): LiveData<Product> {
        return observableProduct
    }

    override fun getFavoriteWithUserId(userId: Long): LiveData<List<Product>> {
        TODO("Not yet implemented")
    }

    override fun getOneFavoriteWithUserId(id: Long, userId: Long): LiveData<Product> {
        TODO("Not yet implemented")
    }

    override fun insertToFavorite(product: Product) {
        TODO("Not yet implemented")
    }

    override fun deleteFromFavorite(product: Product) {
        TODO("Not yet implemented")
    }

    override fun deleteAllFavorites() {
        TODO("Not yet implemented")
    }
}