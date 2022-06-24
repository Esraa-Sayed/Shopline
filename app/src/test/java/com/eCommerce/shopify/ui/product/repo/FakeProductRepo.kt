package com.eCommerce.shopify.ui.product.repo

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eCommerce.shopify.model.ImageProduct
import com.eCommerce.shopify.model.Product
import com.eCommerce.shopify.model.ProductDetail
import com.eCommerce.shopify.model.Products
import retrofit2.Response

class FakeProductRepo: ProductRepoInterface {

    private var products = Products(mutableListOf(
        Product("gid://shopify/Product/7249179246769",
            "The Stan Smith owned the tennis court in the '70s. Today it runs the streets with the same clean, classic style. These kids' shoes preserve the iconic look of the original, made in leather with punched 3-Stripes, heel and tongue logos and lightweight step-in cushioning.",
            "2022-06-18T12:33:03+02:00", "adidas-kids-stan-smith", 7249179246769,
            null, null, mutableListOf(), mutableListOf(), "SHOES",
            "2022-06-18T12:33:03+02:00", "web", "active",
            "adidas, egnition-sample-data, kid", "ADIDAS | KID'S STAN SMITH",
            "2022-06-23T13:45:28+02:00", mutableListOf(), "ADIDAS", false, 1)
    ))

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

    private val observableProductDetailList = MutableLiveData<List<ProductDetail>>().apply {
        postValue(productDetailList)
    }

    override suspend fun getCategoryProducts(id: Long): Response<Products> {
        return Response.success(products)
    }

    override val allProductInShoppingCart: LiveData<List<ProductDetail>>
        get() = observableProductDetailList

    override fun isUserLogin(context: Context): Boolean {
        return false
    }
}