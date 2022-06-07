package com.eCommerce.shopify.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Image(
    //val alt: Any,
    @SerializedName("created_at")
    val createdAt: String,
    val height: Int,
    val src: String?,
    val width: Int
): Parcelable