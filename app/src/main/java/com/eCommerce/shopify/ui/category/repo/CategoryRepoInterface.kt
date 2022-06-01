package com.eCommerce.shopify.ui.category.repo

import com.eCommerce.shopify.model.CustomCollectionsCategory
import retrofit2.Response

interface CategoryRepoInterface {
    suspend fun getCustomCollectionsCategory(): Response<CustomCollectionsCategory>
}