package com.eCommerce.shopify.model

data class Order(
    val created_at: String,
    val id: Long,
    val name: String,
    val total_price: String
)