package com.eCommerce.shopify.model


import com.google.gson.annotations.SerializedName

data class BrandProductsResponse(
    @SerializedName("products")
    val products: List<Product>
)