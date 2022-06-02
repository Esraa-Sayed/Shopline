package com.eCommerce.shopify.model.orderDetails

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LineItem(
    val discount_allocations: List<DiscountAllocation>? = null,
    val fulfillable_quantity: Int? = null,
    val fulfillment_service: String? = null,
    val gift_card: Boolean? = null,
    val grams: Int? = null,
    val id: Long? = null,
    val name: String? = null,
    val price: String? = null,
    val price_set: PriceSet? = null,
    val product_exists: Boolean? = null,
    val product_id: Long? = null,
    val properties: List<Property>? = null,
    val quantity: Int? = null,
    val requires_shipping: Boolean? = null,
    val sku: String? = null,
    val tax_lines: List<TaxLine>? = null,
    val taxable: Boolean? = null,
    val title: String? = null,
    val total_discount: String? = null,
    val total_discount_set: TotalDiscountSet? = null,
    val variant_id: Long? = null,
    val variant_inventory_management: String? = null,
    val variant_title: String? = null,
):Parcelable