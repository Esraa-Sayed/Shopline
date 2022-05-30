package com.eCommerce.shopify.ui.home.repo

import com.eCommerce.shopify.model.SmartCollectionsBrand
import retrofit2.Response

interface HomeRepoInterface {
    suspend fun getSmartCollectionsBrand(): Response<SmartCollectionsBrand>
}