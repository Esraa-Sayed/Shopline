package com.eCommerce.shopify.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CustomCollection(
    @SerializedName("admin_graphql_api_id")
    val adminGraphqlApiId: String,
    @SerializedName("body_html")
    //val bodyHtml: Any,
    val handle: String,
    val id: Long,
    val image: Image?,
    @SerializedName("published_at")
    val publishedAt: String,
    @SerializedName("published_scope")
    val publishedScope: String,
    @SerializedName("sort_order")
    val sortOrder: String,
//    @SerializedName("template_suffix")
    //val templateSuffix: Any,
    val title: String,
    @SerializedName("updated_at")
    val updatedAt: String
): Parcelable