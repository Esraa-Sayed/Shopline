package com.eCommerce.shopify.model

data class CartItem(
    var imageUrl: String,
    var name: String,
    var amount: Int,
    var price: Double
)
