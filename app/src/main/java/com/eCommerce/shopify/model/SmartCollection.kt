package com.eCommerce.shopify.model


import com.google.gson.annotations.SerializedName

data class SmartCollection(
    @SerializedName("admin_graphql_api_id")
    val adminGraphqlApiId: String,
    @SerializedName("body_html")
    val bodyHtml: String,
    val disjunctive: Boolean,
    val handle: String,
    val id: Long,
    val image: Image,
    @SerializedName("published_at")
    val publishedAt: String,
    @SerializedName("published_scope")
    val publishedScope: String,
    val rules: List<Rule>,
    @SerializedName("sort_order")
    val sortOrder: String,
    @SerializedName("template_suffix")
    val templateSuffix: Any,
    val title: String,
    @SerializedName("updated_at")
    val updatedAt: String
)