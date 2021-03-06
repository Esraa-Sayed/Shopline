package com.eCommerce.shopify.ui.product.repo

import android.content.Context
import androidx.lifecycle.LiveData
import com.eCommerce.shopify.database.shoppingcart.ShoppingCartLocalSource
import com.eCommerce.shopify.database.shoppingcart.ShoppingCartLocalSourceInterface
import com.eCommerce.shopify.model.ProductDetail
import com.eCommerce.shopify.model.Products
import com.eCommerce.shopify.model.UserData
import com.eCommerce.shopify.network.RemoteSource
import com.eCommerce.shopify.utils.AppConstants
import com.eCommerce.shopify.utils.AppConstants.IS_LOGIN
import com.eCommerce.shopify.utils.AppConstants.PREFRENCE_File
import com.eCommerce.shopify.utils.AppConstants.USER_EMAIL
import com.eCommerce.shopify.utils.AppSharedPref
import retrofit2.Response

class ProductRepo private constructor(
    private var remoteSource: RemoteSource,
    private var shoppingCartLocalSource: ShoppingCartLocalSourceInterface
) : ProductRepoInterface {

    companion object {
        private var instance: ProductRepoInterface? = null
        fun getInstance(remoteSource: RemoteSource, shoppingCartLocalSource: ShoppingCartLocalSourceInterface): ProductRepoInterface {
            return instance ?: ProductRepo(remoteSource, shoppingCartLocalSource)
        }
    }

    override suspend fun getCategoryProducts(id: Long): Response<Products> {
        return remoteSource.getCategoryProducts(id)
    }

    override val allProductInShoppingCart: LiveData<List<ProductDetail>>
        get() = shoppingCartLocalSource.allProductInShoppingCart

    override fun isUserLogin(context: Context): Boolean {
        return AppSharedPref.getInstance(context, PREFRENCE_File).getBooleanValue(IS_LOGIN, false)
    }
}