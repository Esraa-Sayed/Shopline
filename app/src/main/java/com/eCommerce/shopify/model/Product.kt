package com.eCommerce.shopify.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "favorites")
data class Product(
    @SerializedName("admin_graphql_api_id")
    val adminGraphqlApiId: String,
    @SerializedName("body_html")
    val bodyHtml: String,
    @SerializedName("created_at")
    val createdAt: String,
    val handle: String,
    @PrimaryKey
    val id: Long,
    var userId:Long? = null,
    val image: ImageProduct?,
    val images: List<ImageProduct>,
    val options: List<Option>,
    @SerializedName("product_type")
    val productType: String,
    @SerializedName("published_at")
    val publishedAt: String,
    @SerializedName("published_scope")
    val publishedScope: String,
    val status: String,
    val tags: String,
    //@SerializedName("template_suffix")
    //val templateSuffix: Any,
    val title: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("variants")
    val variants: List<Variant>,
    val vendor: String,
    var isFavorite:Boolean = false,
    val quantity:Int
)