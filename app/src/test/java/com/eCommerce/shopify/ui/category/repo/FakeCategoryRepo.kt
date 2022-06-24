package com.eCommerce.shopify.ui.category.repo

import androidx.lifecycle.MutableLiveData
import com.eCommerce.shopify.model.*
import retrofit2.Response

class FakeCategoryRepo: CategoryRepoInterface {

    private var customCollectionsCategory = CustomCollectionsCategory(mutableListOf(
        CustomCollection("gid://shopify/Collection/287182061745", "kid",
            287182061745, Image("2022-06-18T12:34:33+02:00", 490,
                "https://cdn.shopify.com/s/files/1/0621/2488/4145/collections/3b6a545a8f309a6085625bcadcb19712.jpg?v=1655548473",
                736),
            "2022-06-18T12:34:33+02:00", "web", "best-selling",
            "KID", "2022-06-24T13:35:42+02:00")
    ))

    override suspend fun getCustomCollectionsCategory(): Response<CustomCollectionsCategory> {
        return Response.success(customCollectionsCategory)
    }
}