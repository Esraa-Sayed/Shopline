package com.eCommerce.shopify.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Option(
    val id: Long,
    val name: String,
    val position: Int,
    @SerializedName("product_id")
    val productId: Long,
    val values: List<String>
): Parcelable