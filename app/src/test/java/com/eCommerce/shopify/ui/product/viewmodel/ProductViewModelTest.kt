package com.eCommerce.shopify.ui.product.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.eCommerce.shopify.ui.product.repo.FakeProductRepo
import getOrAwaitValue
import junit.framework.Assert.assertEquals
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProductViewModelTest {

    lateinit var productViewModel: ProductViewModel
    lateinit var repo: FakeProductRepo

    @get:Rule
    var instanceTaskExecutor = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        // GIVEN
        repo = FakeProductRepo()
        productViewModel = ProductViewModel(repo)
    }

    @Test
    fun getCustomCollectionsCategory_remoteCustomCollectionsCategory() {
        //When
        productViewModel.getCategoryProducts(287182061745)

        // THEN
        val liveDataValue = productViewModel.categoryProductsResponse.getOrAwaitValue { }
        Assert.assertThat(liveDataValue, CoreMatchers.not(CoreMatchers.nullValue()))
    }

    @Test
    fun getAllProductInShoppingCartList_localAllProductInShoppingCartList() {
        //When
        val liveDataValue = productViewModel.getAllProductInShoppingCartList().getOrAwaitValue { }

        // THEN
//        val liveDataValue = productViewModel.getAllProductInShoppingCartList().getOrAwaitValue { }
        Assert.assertThat(liveDataValue, CoreMatchers.not(CoreMatchers.nullValue()))
    }

    @Test
    fun getIsUserLogin_appContext_falseValue() {
        //When
        val result = productViewModel.isUserLogin(ApplicationProvider.getApplicationContext())

        // THEN
        assertEquals(result, false)
    }
}