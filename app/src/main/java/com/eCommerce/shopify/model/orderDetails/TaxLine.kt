package com.eCommerce.shopify.model.orderDetails

data class TaxLine(
    val channel_liable: Any,
    val price: String,
    val price_set: PriceSet,
    val rate: Double,
    val title: String
)