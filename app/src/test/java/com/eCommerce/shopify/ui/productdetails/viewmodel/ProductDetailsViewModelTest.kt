package com.eCommerce.shopify.ui.productdetails.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.eCommerce.shopify.ui.productdetails.repo.FakeProductDetailsRepo
import com.eCommerce.shopify.utils.AppConstants.EGP
import getOrAwaitValue
import junit.framework.Assert.assertEquals
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProductDetailsViewModelTest {

    lateinit var productDetailsViewModel: ProductDetailsViewModel
    lateinit var repo: FakeProductDetailsRepo

    @get:Rule
    var instanceTaskExecutor = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        // GIVEN
        repo = FakeProductDetailsRepo()
        productDetailsViewModel = ProductDetailsViewModel(repo)
    }

    @Test
    fun getCurrencyWithUserEmail_returnEGP() {
        //When
        val result = productDetailsViewModel.getCurrencyWithUserEmail(ApplicationProvider.getApplicationContext())

        // THEN
        assertEquals(result, EGP)
    }

    @Test
    fun getProductDetails_remoteProductDetails() {
        //When
        productDetailsViewModel.getCategoryProducts(ApplicationProvider.getApplicationContext(), 7249179246769)

        // THEN
        val liveDataValue = productDetailsViewModel.productDetailsResponse.getOrAwaitValue { }
        Assert.assertThat(liveDataValue, CoreMatchers.not(CoreMatchers.nullValue()))
    }

    @Test
    fun getFavoriteProduct_localFavoriteProduct() {
        //When
        val liveDataValue = productDetailsViewModel.getFavoriteProduct(7249179246769).getOrAwaitValue { }

        // THEN
        Assert.assertThat(liveDataValue, CoreMatchers.not(CoreMatchers.nullValue()))
    }

    @Test
    fun getIsUserLogin_appContext_falseValue() {
        //When
        val result = productDetailsViewModel.isUserLogin(ApplicationProvider.getApplicationContext())

        // THEN
        assertEquals(result, false)
    }
}