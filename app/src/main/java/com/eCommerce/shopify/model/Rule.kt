package com.eCommerce.shopify.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Rule(
    val column: String,
    val condition: String,
    val relation: String
): Parcelable