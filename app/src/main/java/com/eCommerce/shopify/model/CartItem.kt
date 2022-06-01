package com.eCommerce.shopify.model

import java.io.Serializable

data class CartItem(
    val id: Int,
    val imageUrl: String,
    val productName: String,
    val price: Double
): Serializable