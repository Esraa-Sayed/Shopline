package com.eCommerce.shopify.model


import com.google.gson.annotations.SerializedName

data class ProductOption(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("position")
    val position: Int,
    @SerializedName("product_id")
    val productId: Long,
    @SerializedName("values")
    val values: List<String>
)