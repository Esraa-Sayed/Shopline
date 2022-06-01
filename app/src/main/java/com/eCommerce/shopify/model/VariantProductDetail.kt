package com.eCommerce.shopify.model


import com.google.gson.annotations.SerializedName

data class VariantProductDetail(
    @SerializedName("admin_graphql_api_id")
    val adminGraphqlApiId: String,
    val barcode: Any,
    @SerializedName("compare_at_price")
    val compareAtPrice: Any,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("fulfillment_service")
    val fulfillmentService: String,
    val grams: Int,
    val id: Long,
    @SerializedName("image_id")
    val imageId: Any,
    @SerializedName("inventory_item_id")
    val inventoryItemId: Long,
    @SerializedName("inventory_management")
    val inventoryManagement: String,
    @SerializedName("inventory_policy")
    val inventoryPolicy: String,
    @SerializedName("inventory_quantity")
    val inventoryQuantity: Int,
    @SerializedName("old_inventory_quantity")
    val oldInventoryQuantity: Int,
    val option1: String,
    val option2: String,
    val option3: Any,
    val position: Int,
    val price: String,
    @SerializedName("product_id")
    val productId: Long,
    @SerializedName("requires_shipping")
    val requiresShipping: Boolean,
    val sku: String,
    val taxable: Boolean,
    val title: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    val weight: Double,
    @SerializedName("weight_unit")
    val weightUnit: String
)