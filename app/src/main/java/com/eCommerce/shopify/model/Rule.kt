package com.eCommerce.shopify.model


import com.google.gson.annotations.SerializedName

data class Rule(
    val column: String,
    val condition: String,
    val relation: String
)