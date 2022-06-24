package com.eCommerce.shopify.ui.home.repo

import androidx.lifecycle.MutableLiveData
import com.eCommerce.shopify.model.Image
import com.eCommerce.shopify.model.Rule
import com.eCommerce.shopify.model.SmartCollection
import com.eCommerce.shopify.model.SmartCollectionsBrand
import retrofit2.Response

class FakeHomeRepo: HomeRepoInterface {

    private var smartCollectionsBrand = SmartCollectionsBrand(mutableListOf(
        SmartCollection("gid://shopify/Collection/287181635761",
            "Adidas collection", false, "adidas", 287181635761,
            Image("2022-06-18T12:34:18+02:00", 676, "https://cdn.shopify.com/s/files/1/0621/2488/4145/collections/97a3b1227876bf099d279fd38290e567.jpg?v=1655548458", 1000),
            "2022-06-18T12:34:18+02:00", "web",
            listOf(Rule("title", "ADIDAS", "contains")),
            "best-selling", "ADIDAS", "2022-06-23T13:45:28+02:00"
        )
    ))

    override suspend fun getSmartCollectionsBrand(): Response<SmartCollectionsBrand> {
        return Response.success(smartCollectionsBrand)
    }
}