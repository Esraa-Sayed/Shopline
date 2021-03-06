package com.eCommerce.shopify.model.orderDetails

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShippingLine(
    //val carrier_identifier: Any,
    val code: String,
    //val delivery_category: Any,
    //val discount_allocations: List<Any>,
    val discounted_price: String,
    val discounted_price_set: DiscountedPriceSet,
    val id: Int,
    val phone: String,
    val price: String,
    val price_set: PriceSet,
    //val requested_fulfillment_service_id: Any,
    val source: String,
    //val tax_lines: List<Any>,
    val title: String
): Parcelable