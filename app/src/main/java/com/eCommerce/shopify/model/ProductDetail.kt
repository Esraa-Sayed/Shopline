package com.eCommerce.shopify.model


import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "shopping_cart")
data class ProductDetail(
    @SerializedName("admin_graphql_api_id")
    @ColumnInfo(name = "admin_graphql_api_id")
    val adminGraphqlApiId: String,
    @SerializedName("body_html")
    @ColumnInfo(name = "body_html")
    val bodyHtml: String,
    @SerializedName("created_at")
    @ColumnInfo(name = "created_at")
    val createdAt: String,
    val handle: String,
    @PrimaryKey
    val id: Long,
    val image: ImageProduct,
    val images: List<ImageProduct>,
    val options: List<Option>,
    @SerializedName("product_type")
    @ColumnInfo(name = "product_type")
    val productType: String,
    @SerializedName("published_at")
    @ColumnInfo(name = "published_at")
    val publishedAt: String,
    @SerializedName("published_scope")
    @ColumnInfo(name = "published_scope")
    val publishedScope: String,
    val status: String,
    val tags: String,
    val title: String,
    @SerializedName("updated_at")
    @ColumnInfo(name = "updated_at")
    val updatedAt: String,
    val variants: List<Variant>,
    val vendor: String,
    var amount: Int = 0
): Parcelable