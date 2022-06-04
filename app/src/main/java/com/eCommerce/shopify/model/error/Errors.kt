package com.eCommerce.shopify.model.error


import com.google.gson.annotations.SerializedName

data class Errors(
    @SerializedName("email")
    val email: List<String>? = null,
    @SerializedName("phone")
    val phone: List<String>? = null
)