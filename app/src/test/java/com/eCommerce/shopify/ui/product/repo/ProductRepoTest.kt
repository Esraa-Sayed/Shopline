package com.eCommerce.shopify.ui.product.repo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.eCommerce.shopify.model.ImageProduct
import com.eCommerce.shopify.model.Product
import com.eCommerce.shopify.model.ProductDetail
import com.eCommerce.shopify.model.Products
import com.eCommerce.shopify.network.FakeRemoteSource
import com.eCommerce.shopify.network.FakeShoppingCartLocalSource
import getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProductRepoTest {

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

    private lateinit var remoteSource: FakeRemoteSource
    private lateinit var localSource: FakeShoppingCartLocalSource

    private lateinit var repo: ProductRepoInterface

    @get:Rule
    var instanceTaskExecutor = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        // GIVEN
        remoteSource = FakeRemoteSource(products)
        localSource = FakeShoppingCartLocalSource(productDetailList)
        repo = ProductRepo.getInstance(remoteSource, localSource)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getCategoryProducts_categoryId_remoteCategoryProducts() = runTest {
        // WHEN
        val result = repo.getCategoryProducts(287182061745)

        // THEN ==> we get list from network (remote source)
        assertEquals(result.body(), products)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getAllProductInShoppingCart_localProductInShoppingCart() = runTest {
        // WHEN
        val liveDataValue = repo.allProductInShoppingCart.getOrAwaitValue {  }

        // THEN ==> we get list from room (local source)
        assertEquals(liveDataValue, productDetailList)
    }

    @Test
    fun getIsUserLogin_returnIfLoginOrNot() {
        // WHEN
        val result = repo.isUserLogin(ApplicationProvider.getApplicationContext())

        // THEN ==> we get list from room (local source)
        assertEquals(result, false)
    }
}