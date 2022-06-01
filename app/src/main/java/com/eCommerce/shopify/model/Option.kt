package com.eCommerce.shopify.model

import com.google.gson.annotations.SerializedName

data class Option(
    val id: Long,
    val name: String,
    val position: Int,
    @SerializedName("product_id")
    val productId: Long
)