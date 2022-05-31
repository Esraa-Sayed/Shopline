package com.eCommerce.shopify.model


import com.google.gson.annotations.SerializedName

data class CustomCollectionsCategory(
    @SerializedName("custom_collections")
    val customCollections: List<CustomCollection>
)