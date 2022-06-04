package com.eCommerce.shopify.model.error


import com.google.gson.annotations.SerializedName

data class Error(
    @SerializedName("errors")
    val errors: Errors? = null
)