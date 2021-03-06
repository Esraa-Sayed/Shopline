package com.eCommerce.shopify.ui.productdetails.repo

import android.content.Context
import androidx.lifecycle.LiveData
import com.eCommerce.shopify.database.favorite.LocalSource
import com.eCommerce.shopify.database.favorite.LocalSourceInterface
import com.eCommerce.shopify.database.shoppingcart.ShoppingCartLocalSource
import com.eCommerce.shopify.database.shoppingcart.ShoppingCartLocalSourceInterface
import com.eCommerce.shopify.model.Product
import com.eCommerce.shopify.model.ProductDetail
import com.eCommerce.shopify.model.ProductDetails
import com.eCommerce.shopify.network.RemoteSource
import com.eCommerce.shopify.utils.AppConstants
import com.eCommerce.shopify.utils.AppConstants.EGP
import com.eCommerce.shopify.utils.AppConstants.IS_LOGIN
import com.eCommerce.shopify.utils.AppConstants.PREFRENCE_File
import com.eCommerce.shopify.utils.AppConstants.USER_ID
import com.eCommerce.shopify.utils.AppSharedPref
import retrofit2.Response
import kotlin.math.roundToInt

class ProductDetailsRepo private constructor(
    private var remoteSource: RemoteSource,
    private var localSource: LocalSourceInterface,
    private var shoppingCartLocalSource: ShoppingCartLocalSourceInterface
) : ProductDetailsRepoInterface {

    companion object {
        private var instance: ProductDetailsRepoInterface? = null
        fun getInstance(remoteSource: RemoteSource, localSource: LocalSourceInterface, shoppingCartLocalSource: ShoppingCartLocalSourceInterface): ProductDetailsRepoInterface {
            return instance ?: ProductDetailsRepo(remoteSource, localSource, shoppingCartLocalSource)
        }
    }

    override /*suspend*/ fun getCurrencyWithUserEmail(context: Context): String {
        /*val currentCurrency: String
        val userEmail =
            AppSharedPref.getInstance(context, AppConstants.PREFRENCE_File).getStringValue(
                AppConstants.USER_EMAIL, "")
        currentCurrency = if (userEmail.isBlank() || userEmail.isEmpty()) {
            "EGP"
        } else {
            val userWithEmail = remoteSource.getUserWithEmail(userEmail)
            userWithEmail.body()?.customers?.get(0)?.currency.toString()
        }*/

        return AppSharedPref.getInstance(context, PREFRENCE_File).getStringValue(AppConstants.CURRENCY, EGP)
    }

    override suspend fun getProductDetails(context: Context, id: Long): Response<ProductDetails> {
        val productDetails = remoteSource.getProductDetails(id)

        if (getCurrencyWithUserEmail(context) != EGP) {
            productDetails.body()?.product?.variants?.get(0)?.price = (((productDetails.body()?.product?.variants?.get(0)?.price.toString().toDouble() / 18) * 100).roundToInt() / 100.0).toString()
        }

        if (isUserLogin(context)) {
            productDetails.body()?.product?.userId =
                AppSharedPref.getInstance(context, PREFRENCE_File).getLongValue(USER_ID, 0)
        }

        return productDetails
    }

    override fun getFavoriteProduct(id: Long,userId: Long): LiveData<Product> {
        return localSource.getOneFavoriteWithUserId(id,userId)
    }

    override fun insertToFavorite(product: Product) {
        localSource.insertToFavorite(product)
    }

    override fun deleteFromFavorite(product: Product) {
        localSource.deleteFromFavorite(product)
    }

    override fun getProductInShoppingCart(id: Long): LiveData<ProductDetail> {
        return shoppingCartLocalSource.getProductInShoppingCart(id)
    }

    override fun insertProductInShoppingCart(productDetail: ProductDetail) {
        shoppingCartLocalSource.insertProductInShoppingCart(productDetail)
    }

    override fun deleteProductFromShoppingCart(productDetail: ProductDetail) {
        shoppingCartLocalSource.deleteProductFromShoppingCart(productDetail)
    }

    override fun isUserLogin(context: Context): Boolean {
        return AppSharedPref.getInstance(context, PREFRENCE_File).getBooleanValue(IS_LOGIN, false)
    }

    override fun getUserId(context: Context): Long {
        return AppSharedPref.getInstance(context,PREFRENCE_File).getLongValue(AppConstants.USER_ID,0)
    }
}