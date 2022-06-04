package com.eCommerce.shopify.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageProduct(
    @SerializedName("admin_graphql_api_id")
    val adminGraphqlApiId: String,
    @SerializedName("created_at")
    val createdAt: String,
    val height: Int,
    val id: Long,
    val position: Int,
    @SerializedName("product_id")
    val productId: Long,
    val src: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("variant_ids")
    val variantIds: List<Long>,
    val width: Int
): Parcelable