package com.eCommerce.shopify.ui.home.repo

import com.eCommerce.shopify.model.Image
import com.eCommerce.shopify.model.Rule
import com.eCommerce.shopify.model.SmartCollection
import com.eCommerce.shopify.model.SmartCollectionsBrand
import com.eCommerce.shopify.network.FakeRemoteSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class HomeRepoTest {

    private var smartCollectionsBrand = SmartCollectionsBrand(mutableListOf(
        SmartCollection("gid://shopify/Collection/287181635761",
            "Adidas collection", false, "adidas", 287181635761,
            Image("2022-06-18T12:34:18+02:00", 676, "https://cdn.shopify.com/s/files/1/0621/2488/4145/collections/97a3b1227876bf099d279fd38290e567.jpg?v=1655548458", 1000),
            "2022-06-18T12:34:18+02:00", "web",
            listOf(Rule("title", "ADIDAS", "contains")),
            "best-selling", "ADIDAS", "2022-06-23T13:45:28+02:00"
        )
    ))

    private lateinit var remoteSource: FakeRemoteSource

    private lateinit var repo: HomeRepoInterface

    @Before
    fun setUp() {
        // GIVEN
        remoteSource = FakeRemoteSource(smartCollectionsBrand)
        repo = HomeRepo.getInstance(remoteSource)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getSmartCollectionsBrand_remoteSmartCollectionsBrand() = runTest {
        // WHEN
        val result = repo.getSmartCollectionsBrand()

        // THEN ==> we get list from network (remote source)
        assertEquals(result.body(), smartCollectionsBrand)
    }
}