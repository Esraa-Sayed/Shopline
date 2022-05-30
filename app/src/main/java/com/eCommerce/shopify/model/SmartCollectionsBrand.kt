package com.eCommerce.shopify.model


import com.google.gson.annotations.SerializedName

data class SmartCollectionsBrand(
    @SerializedName("smart_collections")
    val smartCollections: List<SmartCollection>
)