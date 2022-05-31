package com.eCommerce.shopify.model


import com.google.gson.annotations.SerializedName

data class BrandCollectionResponse(
    @SerializedName("products")
    val products: List<Product>
)