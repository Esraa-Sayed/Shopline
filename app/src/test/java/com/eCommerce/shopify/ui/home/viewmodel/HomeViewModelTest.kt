package com.eCommerce.shopify.ui.home.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.eCommerce.shopify.ui.home.repo.FakeHomeRepo
import getOrAwaitValue
import org.hamcrest.CoreMatchers.not
import org.hamcrest.CoreMatchers.nullValue
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeViewModelTest {

    lateinit var homeViewModel: HomeViewModel
    lateinit var repo: FakeHomeRepo

    @get:Rule
    var instanceTaskExecutor = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        // GIVEN
        repo = FakeHomeRepo()
        homeViewModel = HomeViewModel(repo)
    }

    @Test
    fun getSmartCollectionsBrand_remoteSmartCollectionsBrand() {
        //When getSmartCollectionsBrand called
        homeViewModel.getSmartCollectionsBrand()

        // THEN
        val liveDataValue = homeViewModel.smartCollectionsBrandResponse.getOrAwaitValue { }
        Assert.assertThat(liveDataValue, not(nullValue()))
    }
}