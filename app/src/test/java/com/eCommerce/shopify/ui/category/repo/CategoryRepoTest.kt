package com.eCommerce.shopify.ui.category.repo

import com.eCommerce.shopify.model.CustomCollection
import com.eCommerce.shopify.model.CustomCollectionsCategory
import com.eCommerce.shopify.model.Image
import com.eCommerce.shopify.network.FakeRemoteSource
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class CategoryRepoTest {

    private var customCollectionsCategory = CustomCollectionsCategory(mutableListOf(
        CustomCollection("gid://shopify/Collection/287182061745", "kid",
            287182061745, Image("2022-06-18T12:34:33+02:00", 490,
                "https://cdn.shopify.com/s/files/1/0621/2488/4145/collections/3b6a545a8f309a6085625bcadcb19712.jpg?v=1655548473",
                736),
            "2022-06-18T12:34:33+02:00", "web", "best-selling",
            "KID", "2022-06-24T13:35:42+02:00")
    ))

    private lateinit var remoteSource: FakeRemoteSource

    private lateinit var repo: CategoryRepoInterface

    @Before
    fun setUp() {
        // GIVEN
        remoteSource = FakeRemoteSource(customCollectionsCategory)
        repo = CategoryRepo.getInstance(remoteSource)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getCustomCollectionsCategory_remoteCustomCollectionsCategory() = runTest {
        // WHEN
        val result = repo.getCustomCollectionsCategory()

        // THEN ==> we get list from network (remote source)
        assertEquals(result.body(), customCollectionsCategory)
    }
}