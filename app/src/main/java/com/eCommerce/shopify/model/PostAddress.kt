package com.eCommerce.shopify.model

import com.eCommerce.shopify.model.Addresse
import com.google.gson.annotations.SerializedName

data class PostAddress(
    @SerializedName("address")
    val address: Addresse
)
