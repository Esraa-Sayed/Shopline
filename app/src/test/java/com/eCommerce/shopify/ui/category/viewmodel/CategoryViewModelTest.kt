package com.eCommerce.shopify.ui.category.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.eCommerce.shopify.ui.category.repo.FakeCategoryRepo
import getOrAwaitValue
import org.hamcrest.CoreMatchers
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CategoryViewModelTest {

    lateinit var categoryViewModel: CategoryViewModel
    lateinit var repo: FakeCategoryRepo

    @get:Rule
    var instanceTaskExecutor = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        // GIVEN
        repo = FakeCategoryRepo()
        categoryViewModel = CategoryViewModel(repo)
    }

    @Test
    fun getCustomCollectionsCategory_remoteCustomCollectionsCategory() {
        //When getSmartCollectionsBrand called
        categoryViewModel.getCustomCollectionsCategory()

        // THEN
        val liveDataValue = categoryViewModel.customCollectionsCategoryResponse.getOrAwaitValue { }
        assertThat(liveDataValue, CoreMatchers.not(CoreMatchers.nullValue()))
    }
}