package com.eCommerce.shopify.ui.productdetails.repo

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eCommerce.shopify.model.ImageProduct
import com.eCommerce.shopify.model.Product
import com.eCommerce.shopify.model.ProductDetail
import com.eCommerce.shopify.model.ProductDetails
import com.eCommerce.shopify.utils.AppConstants.EGP
import retrofit2.Response

class FakeProductDetailsRepo: ProductDetailsRepoInterface {

    private var productDetails = ProductDetails(
        ProductDetail("gid://shopify/Product/7249179246769",
            "The Stan Smith owned the tennis court in the '70s. Today it runs the streets with the same clean, classic style. These kids' shoes preserve the iconic look of the original, made in leather with punched 3-Stripes, heel and tongue logos and lightweight step-in cushioning.",
            "2022-06-18T12:33:03+02:00", "adidas-kids-stan-smith", 7249179246769,
            null, ImageProduct("gid://shopify/ProductImage/32232047509681",
                "2022-06-18T12:33:04+02:00", 560, 32232047509681, 1,
                7249179246769, "https://cdn.shopify.com/s/files/1/0621/2488/4145/products/7883dc186e15bf29dad696e1e989e914.jpg?v=1655548384",
                "2022-06-18T12:33:04+02:00", mutableListOf(), 635), mutableListOf(), mutableListOf(), "SHOES",
            "2022-06-18T12:33:03+02:00", "web", "active",
            "adidas, egnition-sample-data, kid", "ADIDAS | KID'S STAN SMITH",
            "2022-06-23T13:45:28+02:00", mutableListOf(), "ADIDAS")
    )

    private var product = Product("gid://shopify/Product/7249179246769",
        "The Stan Smith owned the tennis court in the '70s. Today it runs the streets with the same clean, classic style. These kids' shoes preserve the iconic look of the original, made in leather with punched 3-Stripes, heel and tongue logos and lightweight step-in cushioning.",
        "2022-06-18T12:33:03+02:00", "adidas-kids-stan-smith", 7249179246769,
        null, ImageProduct("gid://shopify/ProductImage/32232047509681",
            "2022-06-18T12:33:04+02:00", 560, 32232047509681, 1,
            7249179246769, "https://cdn.shopify.com/s/files/1/0621/2488/4145/products/7883dc186e15bf29dad696e1e989e914.jpg?v=1655548384",
            "2022-06-18T12:33:04+02:00", mutableListOf(), 635), mutableListOf(), mutableListOf(), "SHOES",
        "2022-06-18T12:33:03+02:00", "web", "active",
        "adidas, egnition-sample-data, kid", "ADIDAS | KID'S STAN SMITH",
        "2022-06-23T13:45:28+02:00", mutableListOf(), "ADIDAS", false, 1)

    private var productDetailList = mutableListOf(
        ProductDetail("gid://shopify/Product/7249179246769",
            "The Stan Smith owned the tennis court in the '70s. Today it runs the streets with the same clean, classic style. These kids' shoes preserve the iconic look of the original, made in leather with punched 3-Stripes, heel and tongue logos and lightweight step-in cushioning.",
            "2022-06-18T12:33:03+02:00", "adidas-kids-stan-smith", 7249179246769,
            null, ImageProduct("gid://shopify/ProductImage/32232047509681",
                "2022-06-18T12:33:04+02:00", 560, 32232047509681, 1,
                7249179246769, "https://cdn.shopify.com/s/files/1/0621/2488/4145/products/7883dc186e15bf29dad696e1e989e914.jpg?v=1655548384",
                "2022-06-18T12:33:04+02:00", mutableListOf(), 635), mutableListOf(), mutableListOf(), "SHOES",
            "2022-06-18T12:33:03+02:00", "web", "active",
            "adidas, egnition-sample-data, kid", "ADIDAS | KID'S STAN SMITH",
            "2022-06-23T13:45:28+02:00", mutableListOf(), "ADIDAS")
    )

    private val observableFavoriteProduct = MutableLiveData<Product>().apply {
        postValue(product)
    }

    private val observableProductDetail = MutableLiveData<ProductDetail>().apply {
        postValue(productDetails.product)
    }

    override fun getCurrencyWithUserEmail(context: Context): String {
        return EGP
    }

    override suspend fun getProductDetails(context: Context, id: Long): Response<ProductDetails> {
        return Response.success(productDetails)
    }

    override fun getFavoriteProduct(id: Long): LiveData<Product> {
        return observableFavoriteProduct
    }

    override fun insertToFavorite(product: Product) {
        TODO("Not yet implemented")
    }

    override fun deleteFromFavorite(product: Product) {
        TODO("Not yet implemented")
    }

    override fun getProductInShoppingCart(id: Long): LiveData<ProductDetail> {
        return observableProductDetail
    }

    override fun insertProductInShoppingCart(productDetail: ProductDetail) {
        TODO("Not yet implemented")
    }

    override fun deleteProductFromShoppingCart(productDetail: ProductDetail) {
        TODO("Not yet implemented")
    }

    override fun isUserLogin(context: Context): Boolean {
        return false
    }
}