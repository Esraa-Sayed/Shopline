package com.eCommerce.shopify.model.orderDetails

data class DiscountCode(
    val amount: String,
    val code: String,
    val type: String
)