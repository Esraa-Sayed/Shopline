package com.eCommerce.shopify.model.payPalResponse

data class Response(
    val create_time: String,
    val id: String,
    val intent: String,
    val state: String
)