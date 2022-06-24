package com.eCommerce.shopify.ui.productdetails.repo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.eCommerce.shopify.model.ImageProduct
import com.eCommerce.shopify.model.Product
import com.eCommerce.shopify.model.ProductDetail
import com.eCommerce.shopify.model.ProductDetails
import com.eCommerce.shopify.network.FakeLocalSource
import com.eCommerce.shopify.network.FakeRemoteSource
import com.eCommerce.shopify.network.FakeShoppingCartLocalSource
import com.eCommerce.shopify.utils.AppConstants.EGP
import getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProductDetailsRepoTest {

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

    private lateinit var remoteSource: FakeRemoteSource
    private lateinit var shoppingCartLocalSource: FakeShoppingCartLocalSource
    private lateinit var localSource: FakeLocalSource

    private lateinit var repo: ProductDetailsRepoInterface

    @get:Rule
    var instanceTaskExecutor = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        remoteSource = FakeRemoteSource(productDetails)
        shoppingCartLocalSource = FakeShoppingCartLocalSource(productDetailList)
        localSource = FakeLocalSource(product)
        repo = ProductDetailsRepo.getInstance(remoteSource, localSource, shoppingCartLocalSource)
    }

    @Test
    fun getCurrencyWithUserEmail_appContext_currentCurrency() {
        // WHEN
        val result = repo.getCurrencyWithUserEmail(ApplicationProvider.getApplicationContext())

        // THEN
        assertEquals(result, EGP)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getProductDetails_appContextAndProductId_remoteProductDetails() = runTest {
        // WHEN
        val result = repo.getProductDetails(ApplicationProvider.getApplicationContext(), 7249179246769)

        // THEN ==> we get list from network (remote source)
        assertEquals(result.body(), productDetails)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getFavoriteProduct_productId_localFavoriteProduct() = runTest {
        // WHEN
        val result = repo.getFavoriteProduct(7249179246769).getOrAwaitValue {  }

        // THEN ==> we get list from network (remote source)
        assertEquals(result, product)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getProductInShoppingCart_productId_localProductInShoppingCart() = runTest {
        // WHEN
        val result = repo.getProductInShoppingCart(7249179246769).getOrAwaitValue {  }

        // THEN ==> we get list from network (remote source)
        assertEquals(result, productDetails.product)
    }

    @Test
    fun getIsUserLogin_returnIfLoginOrNot() {
        // WHEN
        val result = repo.isUserLogin(ApplicationProvider.getApplicationContext())

        // THEN ==> we get list from room (local source)
        assertEquals(result, false)
    }
}